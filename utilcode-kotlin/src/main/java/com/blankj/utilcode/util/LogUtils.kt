@file:JvmName("LogUtils")

package com.blankj.utilcode.util

import android.content.ClipData
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.support.annotation.IntDef
import android.support.annotation.IntRange
import android.support.annotation.RequiresApi
import android.support.v4.util.SimpleArrayMap
import android.util.Log
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.*
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.net.UnknownHostException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource


const val V = Log.VERBOSE
const val D = Log.DEBUG
const val I = Log.INFO
const val W = Log.WARN
const val E = Log.ERROR
const val A = Log.ASSERT

@IntDef(V, D, I, W, E, A)
@Retention(AnnotationRetention.SOURCE)
annotation class TYPE

private val T = charArrayOf('V', 'D', 'I', 'W', 'E', 'A')

private val FILE = 0x10
private val JSON = 0x20
private val XML = 0x30

private const val TOP_CORNER = "┌"
private const val MIDDLE_CORNER = "├"
private const val LEFT_BORDER = "│ "
private const val BOTTOM_CORNER = "└"
private const val SIDE_DIVIDER = "────────────────────────────────────────────────────────"
private const val MIDDLE_DIVIDER = "┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄"
private const val TOP_BORDER = TOP_CORNER + SIDE_DIVIDER + SIDE_DIVIDER
private const val MIDDLE_BORDER = MIDDLE_CORNER + MIDDLE_DIVIDER + MIDDLE_DIVIDER
private const val BOTTOM_BORDER = BOTTOM_CORNER + SIDE_DIVIDER + SIDE_DIVIDER
private const val MAX_LEN = 3000
private const val NOTHING = "log nothing"
private const val NULL = "null"
private const val ARGS = "args"
private const val PLACEHOLDER = " "

private val FILE_SEP = System.getProperty("file.separator")
private val LINE_SEP = System.getProperty("line.separator")
private val FORMAT = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS ", Locale.getDefault())
private val CONFIG = Config()

private val EXECUTOR = Executors.newSingleThreadExecutor()

private val I_FORMATTER_MAP = SimpleArrayMap<Class<*>, IFormatter<Any>>()

fun getConfig(): Config {
    return CONFIG
}

fun v(vararg contents: Any) {
    log(V, CONFIG.mGlobalTag, *contents)
}

fun vTag(tag: String, vararg contents: Any) {
    log(V, tag, *contents)
}

fun d(vararg contents: Any) {
    log(D, CONFIG.mGlobalTag, *contents)
}

fun dTag(tag: String, vararg contents: Any) {
    log(D, tag, *contents)
}

fun i(vararg contents: Any) {
    log(I, CONFIG.mGlobalTag, *contents)
}

fun iTag(tag: String, vararg contents: Any) {
    log(I, tag, *contents)
}

fun w(vararg contents: Any) {
    log(W, CONFIG.mGlobalTag, *contents)
}

fun wTag(tag: String, vararg contents: Any) {
    log(W, tag, *contents)
}

fun e(vararg contents: Any) {
    log(E, CONFIG.mGlobalTag, *contents)
}

fun eTag(tag: String, vararg contents: Any) {
    log(E, tag, *contents)
}

fun a(vararg contents: Any) {
    log(A, CONFIG.mGlobalTag, *contents)
}

fun aTag(tag: String, vararg contents: Any) {
    log(A, tag, *contents)
}

fun file(content: Any) {
    log(FILE or D, CONFIG.mGlobalTag, content)
}

fun file(@TYPE type: Int, content: Any) {
    log(FILE or type, CONFIG.mGlobalTag, content)
}

fun file(tag: String, content: Any) {
    log(FILE or D, tag, content)
}

fun file(@TYPE type: Int, tag: String, content: Any) {
    log(FILE or type, tag, content)
}

fun json(content: String) {
    log(JSON or D, CONFIG.mGlobalTag, content)
}

fun json(@TYPE type: Int, content: String) {
    log(JSON or type, CONFIG.mGlobalTag, content)
}

fun json(tag: String, content: String) {
    log(JSON or D, tag, content)
}

fun json(@TYPE type: Int, tag: String, content: String) {
    log(JSON or type, tag, content)
}

fun xml(content: String) {
    log(XML or D, CONFIG.mGlobalTag, content)
}

fun xml(@TYPE type: Int, content: String) {
    log(XML or type, CONFIG.mGlobalTag, content)
}

fun xml(tag: String, content: String) {
    log(XML or D, tag, content)
}

fun xml(@TYPE type: Int, tag: String, content: String) {
    log(XML or type, tag, content)
}

fun log(type: Int, tag: String?, vararg contents: Any) {
    if (!CONFIG.mLogSwitch || !CONFIG.mLog2ConsoleSwitch && !CONFIG.mLog2FileSwitch) return
    val typeLow = type and 0x0f
    val typeHigh = type and 0xf0
    if (typeLow < CONFIG.mConsoleFilter && typeLow < CONFIG.mFileFilter) return
    val tagHead = processTagAndHead(tag)
    val body = processBody(typeHigh, *contents)
    if (CONFIG.mLog2ConsoleSwitch && typeLow >= CONFIG.mConsoleFilter && typeHigh != FILE) {
        print2Console(typeLow, tagHead.tag, tagHead.consoleHead, body)
    }
    if ((CONFIG.mLog2FileSwitch || typeHigh == FILE) && typeLow >= CONFIG.mFileFilter) {
        print2File(typeLow, tagHead.tag, tagHead.fileHead + body)
    }
}


fun isSpace(s: String?): Boolean {
    if (s == null) return true
    var i = 0
    val len = s.length
    while (i < len) {
        if (!Character.isWhitespace(s[i])) {
            return false
        }
        ++i
    }
    return true
}

private fun processTagAndHead(tag: String?): TagHead {
    var tag = NULL
    if (!CONFIG.mTagIsSpace && !CONFIG.mLogHeadSwitch) {
        tag = CONFIG.mGlobalTag
    } else {
        val stackTrace = Throwable().stackTrace
        val stackIndex = 3 + CONFIG.mStackOffset
        if (stackIndex >= stackTrace.size) {
            val targetElement = stackTrace[3]
            val fileName = getFileName(targetElement)
            if (CONFIG.mTagIsSpace) {
                val index = fileName.indexOf('.')// Use proguard may not find '.'.
                tag = if (index == -1) fileName else fileName.substring(0, index)
            }
            return TagHead(tag, null, ": ")
        }
        var targetElement = stackTrace[stackIndex]
        val fileName = getFileName(targetElement)
        if (CONFIG.mTagIsSpace && isSpace(tag)) {
            val index = fileName.indexOf('.')// Use proguard may not find '.'.
            tag = if (index == -1) fileName else fileName.substring(0, index)
        }
        if (CONFIG.mLogHeadSwitch) {
            val tName = Thread.currentThread().name
            val head = Formatter()
                    .format("%s, %s.%s(%s:%d)",
                            tName,
                            targetElement.className,
                            targetElement.methodName,
                            fileName,
                            targetElement.lineNumber)
                    .toString()
            val fileHead = " [$head]: "
            if (CONFIG.mStackDeep <= 1) {
                return TagHead(tag, arrayOf(head), fileHead)
            } else {
                val consoleHead = arrayOfNulls<String>(Math.min(
                        CONFIG.mStackDeep,
                        stackTrace.size - stackIndex
                ))
                consoleHead[0] = head
                val spaceLen = tName.length + 2
                val space = Formatter().format("%" + spaceLen + "s", "").toString()
                var i = 1
                val len = consoleHead.size
                while (i < len) {
                    targetElement = stackTrace[i + stackIndex]
                    consoleHead[i] = Formatter()
                            .format("%s%s.%s(%s:%d)",
                                    space,
                                    targetElement.className,
                                    targetElement.methodName,
                                    getFileName(targetElement),
                                    targetElement.lineNumber)
                            .toString()
                    ++i
                }
                return TagHead(tag, consoleHead, fileHead)
            }
        }
    }
    return TagHead(tag, null, ": ")
}

private fun getFileName(targetElement: StackTraceElement): String {
    val fileName = targetElement.fileName
    if (fileName != null) return fileName
    // If name of file is null, should add
    // "-keepattributes SourceFile,LineNumberTable" in proguard file.
    var className = targetElement.className
    val classNameInfo = className.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    if (classNameInfo.size > 0) {
        className = classNameInfo[classNameInfo.size - 1]
    }
    val index = className.indexOf('$')
    if (index != -1) {
        className = className.substring(0, index)
    }
    return "$className.java"
}

private fun processBody(type: Int, vararg contents: Any): String {
    var body = NULL
    if (contents != null) {
        if (contents.size == 1) {
            body = formatObject(type, contents[0])
        } else {
            val sb = StringBuilder()
            var i = 0
            val len = contents.size
            while (i < len) {
                val content = contents[i]
                sb.append(ARGS)
                        .append("[")
                        .append(i)
                        .append("]")
                        .append(" = ")
                        .append(formatObject(content))
                        .append(LINE_SEP)
                ++i
            }
            body = sb.toString()
        }
    }
    return if (body.length == 0) NOTHING else body
}

private fun formatObject(type: Int, obj: Any?): String {
    if (obj == null) return NULL
    if (type == JSON) return LogFormatter.formatJson(obj.toString())
    return if (type == XML) LogFormatter.formatXml(obj.toString()) else formatObject(obj)
}

private fun formatObject(obj: Any?): String {
    if (obj == null) return NULL
    if (!I_FORMATTER_MAP.isEmpty) {
        val iFormatter = I_FORMATTER_MAP.get(getClassFromObject(obj))
        if (iFormatter != null) {
            return iFormatter.format(obj)
        }
    }
    return when {
        obj.javaClass.isArray -> return LogFormatter.array2String(obj)
        obj is Throwable -> return LogFormatter.throwable2String(obj)
        obj is Bundle -> return LogFormatter.bundle2String(obj)
        obj is Intent -> LogFormatter.intent2String(obj)
        else -> obj.toString()
    }
}

private fun print2Console(type: Int,
                          tag: String,
                          head: Array<String?>?,
                          msg: String) {
    if (CONFIG.mSingleTagSwitch) {
        printSingleTagMsg(type, tag, processSingleTagMsg(type, tag, head, msg))
    } else {
        printBorder(type, tag, true)
        printHead(type, tag, head)
        printMsg(type, tag, msg)
        printBorder(type, tag, false)
    }
}

private fun printBorder(type: Int, tag: String, isTop: Boolean) {
    if (CONFIG.mLogBorderSwitch) {
        Log.println(type, tag, if (isTop) TOP_BORDER else BOTTOM_BORDER)
    }
}

private fun printHead(type: Int, tag: String, head: Array<String?>?) {
    if (head != null) {
        for (aHead in head) {
            Log.println(type, tag, if (CONFIG.mLogBorderSwitch) LEFT_BORDER + aHead else aHead)
        }
        if (CONFIG.mLogBorderSwitch) Log.println(type, tag, MIDDLE_BORDER)
    }
}

private fun printMsg(type: Int, tag: String, msg: String) {
    val len = msg.length
    val countOfSub = len / MAX_LEN
    if (countOfSub > 0) {
        var index = 0
        for (i in 0 until countOfSub) {
            printSubMsg(type, tag, msg.substring(index, index + MAX_LEN))
            index += MAX_LEN
        }
        if (index != len) {
            printSubMsg(type, tag, msg.substring(index, len))
        }
    } else {
        printSubMsg(type, tag, msg)
    }
}

private fun printSubMsg(type: Int, tag: String, msg: String) {
    if (!CONFIG.mLogBorderSwitch) {
        Log.println(type, tag, msg)
        return
    }
    val sb = StringBuilder()
    val lines = msg.split(LINE_SEP.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    for (line in lines) {
        Log.println(type, tag, LEFT_BORDER + line)
    }
}

private fun processSingleTagMsg(type: Int,
                                tag: String,
                                head: Array<String?>?,
                                msg: String): String {
    val sb = StringBuilder()
    sb.append(PLACEHOLDER).append(LINE_SEP)
    if (CONFIG.mLogBorderSwitch) {
        sb.append(TOP_BORDER).append(LINE_SEP)
        if (head != null) {
            for (aHead in head) {
                sb.append(LEFT_BORDER).append(aHead).append(LINE_SEP)
            }
            sb.append(MIDDLE_BORDER).append(LINE_SEP)
        }
        for (line in msg.split(LINE_SEP.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()) {
            sb.append(LEFT_BORDER).append(line).append(LINE_SEP)
        }
        sb.append(BOTTOM_BORDER)
    } else {
        if (head != null) {
            for (aHead in head) {
                sb.append(aHead).append(LINE_SEP)
            }
        }
        sb.append(msg)
    }
    return sb.toString()
}

private fun printSingleTagMsg(type: Int, tag: String, msg: String) {
    val len = msg.length
    val countOfSub = len / MAX_LEN
    if (countOfSub > 0) {
        if (CONFIG.mLogBorderSwitch) {
            Log.println(type, tag, msg.substring(0, MAX_LEN) + LINE_SEP + BOTTOM_BORDER)
            var index = MAX_LEN
            for (i in 1 until countOfSub) {
                Log.println(type, tag, PLACEHOLDER + LINE_SEP + TOP_BORDER + LINE_SEP
                        + LEFT_BORDER + msg.substring(index, index + MAX_LEN)
                        + LINE_SEP + BOTTOM_BORDER)
                index += MAX_LEN
            }
            if (index != len) {
                Log.println(type, tag, PLACEHOLDER + LINE_SEP + TOP_BORDER + LINE_SEP
                        + LEFT_BORDER + msg.substring(index, len))
            }
        } else {
            Log.println(type, tag, msg.substring(0, MAX_LEN))
            var index = MAX_LEN
            for (i in 1 until countOfSub) {
                Log.println(type, tag,
                        PLACEHOLDER + LINE_SEP + msg.substring(index, index + MAX_LEN))
                index += MAX_LEN
            }
            if (index != len) {
                Log.println(type, tag, PLACEHOLDER + LINE_SEP + msg.substring(index, len))
            }
        }
    } else {
        Log.println(type, tag, msg)
    }
}

private fun print2File(type: Int, tag: String, msg: String) {
    val now = Date(System.currentTimeMillis())
    val format = FORMAT.format(now)
    val date = format.substring(0, 10)
    val time = format.substring(11)
    val fullPath = ((if (CONFIG.mDir == null) CONFIG.mDefaultDir else CONFIG.mDir)
            + CONFIG.mFilePrefix + "-" + date + ".txt")
    if (!createOrExistsFile(fullPath)) {
        Log.e("LogUtils", "create $fullPath failed!")
        return
    }
    val sb = StringBuilder()
    sb.append(time)
            .append(T[type - V])
            .append("/")
            .append(tag)
            .append(msg)
            .append(LINE_SEP)
    val content = sb.toString()
    input2File(content, fullPath)
}

private fun createOrExistsFile(filePath: String): Boolean {
    val file = File(filePath)
    if (file.exists()) return file.isFile()
    if (!createOrExistsDir(file.getParentFile())) return false
    try {
        deleteDueLogs(filePath)
        val isCreate = file.createNewFile()
        if (isCreate) {
            printDeviceInfo(filePath)
        }
        return isCreate
    } catch (e: IOException) {
        e.printStackTrace()
        return false
    }

}

private fun deleteDueLogs(filePath: String) {
    val file = File(filePath)
    val parentFile = file.parentFile
    val files = parentFile.listFiles { _, name ->
        name.matches(("^" + CONFIG.mFilePrefix + "-[0-9]{4}-[0-9]{2}-[0-9]{2}.txt$").toRegex())
    }
    if (files.isEmpty()) return
    val length = filePath.length
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    try {
        val curDay = filePath.substring(length - 14, length - 4)
        val dueMillis = sdf.parse(curDay).time - CONFIG.mSaveDays * 86400000L
        for (aFile in files) {
            val name = aFile.getName()
            val l = name.length
            val logDay = name.substring(l - 14, l - 4)
            if (sdf.parse(logDay).time <= dueMillis) {
                EXECUTOR.execute {
                    val delete = aFile.delete()
                    if (!delete) {
                        Log.e("LogUtils", "delete $aFile failed!")
                    }
                }
            }
        }
    } catch (e: ParseException) {
        e.printStackTrace()
    }

}

private fun printDeviceInfo(filePath: String) {
    var versionName = ""
    var versionCode = 0
    try {
        val pi = getApp()
                .packageManager
                .getPackageInfo(getApp().packageName, 0)
        if (pi != null) {
            versionName = pi.versionName
            versionCode = pi.versionCode
        }
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }

    val time = filePath.substring(filePath.length - 14, filePath.length - 4)
    val head = "************* Log Head ****************" +
            "\nDate of Log        : " + time +
            "\nDevice Manufacturer: " + Build.MANUFACTURER +
            "\nDevice Model       : " + Build.MODEL +
            "\nAndroid Version    : " + Build.VERSION.RELEASE +
            "\nAndroid SDK        : " + Build.VERSION.SDK_INT +
            "\nApp VersionName    : " + versionName +
            "\nApp VersionCode    : " + versionCode +
            "\n************* Log Head ****************\n\n"
    input2File(head, filePath)
}

private fun createOrExistsDir(file: File?): Boolean {
    return file != null && if (file!!.exists()) file!!.isDirectory() else file!!.mkdirs()
}

private fun input2File(input: String, filePath: String) {
    EXECUTOR.execute(Runnable {
        var bw: BufferedWriter? = null
        try {
            bw = BufferedWriter(FileWriter(filePath, true))
            bw!!.write(input)
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("LogUtils", "log to $filePath failed!")
        } finally {
            try {
                if (bw != null) {
                    bw!!.close()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    })
}

class Config internal constructor() {
    var mDefaultDir: String? = null// The default storage directory of log.
    var mDir: String? = null       // The storage directory of log.
    var mFilePrefix = "util"// The file prefix of log.
    var mLogSwitch = true  // The switch of log.
    var mLog2ConsoleSwitch = true  // The logcat's switch of log.
    var mGlobalTag: String = ""  // The global tag of log.
    var mTagIsSpace = true  // The global tag is space.
    var mLogHeadSwitch = true  // The head's switch of log.
    var mLog2FileSwitch = false // The file's switch of log.
    var mLogBorderSwitch = true  // The border's switch of log.
    var mSingleTagSwitch = true  // The single tag of log.
    var mConsoleFilter = V     // The console's filter of log.
    var mFileFilter = V     // The file's filter of log.
    var mStackDeep = 1     // The stack's deep of log.
    var mStackOffset = 0     // The stack's offset of log.
    var mSaveDays = -1    // The save days of log.

    init {
        if (mDefaultDir == null) {
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                    && getApp().externalCacheDir != null)
                mDefaultDir = getApp().externalCacheDir.toString() + FILE_SEP + "log" + FILE_SEP
            else {
                mDefaultDir = getApp().cacheDir.toString() + FILE_SEP + "log" + FILE_SEP
            }
        }
    }

    fun setLogSwitch(logSwitch: Boolean): Config {
        mLogSwitch = logSwitch
        return this
    }

    fun setConsoleSwitch(consoleSwitch: Boolean): Config {
        mLog2ConsoleSwitch = consoleSwitch
        return this
    }

    fun setGlobalTag(tag: String): Config {
        if (isSpace(tag)) {
            mGlobalTag = ""
            mTagIsSpace = true
        } else {
            mGlobalTag = tag
            mTagIsSpace = false
        }
        return this
    }

    fun setLogHeadSwitch(logHeadSwitch: Boolean): Config {
        mLogHeadSwitch = logHeadSwitch
        return this
    }

    fun setLog2FileSwitch(log2FileSwitch: Boolean): Config {
        mLog2FileSwitch = log2FileSwitch
        return this
    }

    fun setDir(dir: String): Config {
        if (isSpace(dir)) {
            mDir = null
        } else {
            mDir = if (dir.endsWith(FILE_SEP)) dir else dir + FILE_SEP
        }
        return this
    }

    fun setDir(dir: File?): Config {
        mDir = if (dir == null) null else dir!!.getAbsolutePath() + FILE_SEP
        return this
    }

    fun setFilePrefix(filePrefix: String): Config {
        if (isSpace(filePrefix)) {
            mFilePrefix = "util"
        } else {
            mFilePrefix = filePrefix
        }
        return this
    }

    fun setBorderSwitch(borderSwitch: Boolean): Config {
        mLogBorderSwitch = borderSwitch
        return this
    }

    fun setSingleTagSwitch(singleTagSwitch: Boolean): Config {
        mSingleTagSwitch = singleTagSwitch
        return this
    }

    fun setConsoleFilter(@TYPE consoleFilter: Int): Config {
        mConsoleFilter = consoleFilter
        return this
    }

    fun setFileFilter(@TYPE fileFilter: Int): Config {
        mFileFilter = fileFilter
        return this
    }

    fun setStackDeep(@IntRange(from = 1) stackDeep: Int): Config {
        mStackDeep = stackDeep
        return this
    }

    fun setStackOffset(@IntRange(from = 0) stackOffset: Int): Config {
        mStackOffset = stackOffset
        return this
    }

    fun setSaveDays(@IntRange(from = 1) saveDays: Int): Config {
        mSaveDays = saveDays
        return this
    }

    fun addFormatter(iFormatter: IFormatter<Any>): Config {
        I_FORMATTER_MAP.put(getTypeClassFromParadigm(iFormatter), iFormatter)
        return this
    }

    override fun toString(): String {
        return ("switch: " + mLogSwitch
                + LINE_SEP + "console: " + mLog2ConsoleSwitch
                + LINE_SEP + "tag: " + (if (mTagIsSpace) "null" else mGlobalTag)
                + LINE_SEP + "head: " + mLogHeadSwitch
                + LINE_SEP + "file: " + mLog2FileSwitch
                + LINE_SEP + "dir: " + (if (mDir == null) mDefaultDir else mDir)
                + LINE_SEP + "filePrefix: " + mFilePrefix
                + LINE_SEP + "border: " + mLogBorderSwitch
                + LINE_SEP + "singleTag: " + mSingleTagSwitch
                + LINE_SEP + "consoleFilter: " + T[mConsoleFilter - V]
                + LINE_SEP + "fileFilter: " + T[mFileFilter - V]
                + LINE_SEP + "stackDeep: " + mStackDeep
                + LINE_SEP + "stackOffset: " + mStackOffset
                + LINE_SEP + "saveDays: " + mSaveDays
                + LINE_SEP + "formatter: " + I_FORMATTER_MAP)
    }
}

abstract class IFormatter<T> {
    abstract fun format(t: T): String
}

private class TagHead internal constructor(internal var tag: String,
                                           internal var consoleHead: Array<String?>?,
                                           internal var fileHead: String)

private object LogFormatter {
    internal fun formatJson(json: String): String {
        var json = json
        try {
            if (json.startsWith("{")) {
                json = JSONObject(json).toString(4)
            } else if (json.startsWith("[")) {
                json = JSONArray(json).toString(4)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return json
    }

    internal fun formatXml(xml: String): String {
        var xml = xml
        try {
            val xmlInput = StreamSource(StringReader(xml))
            val xmlOutput = StreamResult(StringWriter())
            val transformer = TransformerFactory.newInstance().newTransformer()
            transformer.setOutputProperty(OutputKeys.INDENT, "yes")
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4")
            transformer.transform(xmlInput, xmlOutput)
            xml = xmlOutput.getWriter().toString().replaceFirst(">", ">$LINE_SEP")
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return xml
    }

    internal fun array2String(obj: Any): String {
        if (obj is Array<*>) {
            return Arrays.deepToString(obj)
        } else if (obj is BooleanArray) {
            return Arrays.toString(obj)
        } else if (obj is ByteArray) {
            return Arrays.toString(obj)
        } else if (obj is CharArray) {
            return Arrays.toString(obj)
        } else if (obj is DoubleArray) {
            return Arrays.toString(obj)
        } else if (obj is FloatArray) {
            return Arrays.toString(obj)
        } else if (obj is IntArray) {
            return Arrays.toString(obj)
        } else if (obj is LongArray) {
            return Arrays.toString(obj)
        } else if (obj is ShortArray) {
            return Arrays.toString(obj)
        }
        throw IllegalArgumentException("Array has incompatible type: " + obj.javaClass)
    }

    internal fun throwable2String(e: Throwable): String {
        var t: Throwable? = e
        while (t != null) {
            if (t is UnknownHostException) {
                return ""
            }
            t = t.cause
        }
        val sw = StringWriter()
        val pw = PrintWriter(sw)
        e.printStackTrace(pw)
        var cause: Throwable? = e.cause
        while (cause != null) {
            cause.printStackTrace(pw)
            cause = cause.cause
        }
        pw.flush()
        return sw.toString()
    }

    internal fun bundle2String(bundle: Bundle): String {
        val iterator = bundle.keySet().iterator()
        if (!iterator.hasNext()) {
            return "Bundle {}"
        }
        val sb = StringBuilder(128)
        sb.append("Bundle { ")
        while (true) {
            val key = iterator.next()
            val value = bundle.get(key)
            sb.append(key).append('=')
            if (value != null && value is Bundle) {
                sb.append(if (value === bundle) "(this Bundle)" else bundle2String(value))
            } else {
                sb.append(formatObject(value))
            }
            if (!iterator.hasNext()) return sb.append(" }").toString()
            sb.append(',').append(' ')
        }
    }

    internal fun intent2String(intent: Intent): String {
        val sb = StringBuilder(128)
        sb.append("Intent { ")
        var first = true
        val mAction = intent.action
        if (mAction != null) {
            sb.append("act=").append(mAction)
            first = false
        }
        val mCategories = intent.categories
        if (mCategories != null) {
            if (!first) {
                sb.append(' ')
            }
            first = false
            sb.append("cat=[")
            var firstCategory = true
            for (c in mCategories) {
                if (!firstCategory) {
                    sb.append(',')
                }
                sb.append(c)
                firstCategory = false
            }
            sb.append("]")
        }
        val mData = intent.data
        if (mData != null) {
            if (!first) {
                sb.append(' ')
            }
            first = false
            sb.append("dat=").append(mData)
        }
        val mType = intent.type
        if (mType != null) {
            if (!first) {
                sb.append(' ')
            }
            first = false
            sb.append("typ=").append(mType)
        }
        val mFlags = intent.flags
        if (mFlags != 0) {
            if (!first) {
                sb.append(' ')
            }
            first = false
            sb.append("flg=0x").append(Integer.toHexString(mFlags))
        }
        val mPackage = intent.getPackage()
        if (mPackage != null) {
            if (!first) {
                sb.append(' ')
            }
            first = false
            sb.append("pkg=").append(mPackage)
        }
        val mComponent = intent.component
        if (mComponent != null) {
            if (!first) {
                sb.append(' ')
            }
            first = false
            sb.append("cmp=").append(mComponent.flattenToShortString())
        }
        val mSourceBounds = intent.sourceBounds
        if (mSourceBounds != null) {
            if (!first) {
                sb.append(' ')
            }
            first = false
            sb.append("bnds=").append(mSourceBounds.toShortString())
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            val mClipData = intent.clipData
            if (mClipData != null) {
                if (!first) {
                    sb.append(' ')
                }
                first = false
                clipData2String(mClipData, sb)
            }
        }
        val mExtras = intent.extras
        if (mExtras != null) {
            if (!first) {
                sb.append(' ')
            }
            first = false
            sb.append("extras={")
            sb.append(bundle2String(mExtras))
            sb.append('}')
        }
        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            val mSelector = intent.selector
            if (mSelector != null) {
                if (!first) {
                    sb.append(' ')
                }
                first = false
                sb.append("sel={")
                sb.append(if (mSelector === intent) "(this Intent)" else intent2String(mSelector))
                sb.append("}")
            }
        }
        sb.append(" }")
        return sb.toString()
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private fun clipData2String(clipData: ClipData, sb: StringBuilder) {
        val item = clipData.getItemAt(0)
        if (item == null) {
            sb.append("ClipData.Item {}")
            return
        }
        sb.append("ClipData.Item { ")
        val mHtmlText = item.htmlText
        if (mHtmlText != null) {
            sb.append("H:")
            sb.append(mHtmlText)
            sb.append("}")
            return
        }
        val mText = item.text
        if (mText != null) {
            sb.append("T:")
            sb.append(mText)
            sb.append("}")
            return
        }
        val uri = item.uri
        if (uri != null) {
            sb.append("U:").append(uri)
            sb.append("}")
            return
        }
        val intent = item.intent
        if (intent != null) {
            sb.append("I:")
            sb.append(intent2String(intent))
            sb.append("}")
            return
        }
        sb.append("NULL")
        sb.append("}")
    }
}

fun <T> getTypeClassFromParadigm(formatter: IFormatter<T>): Class<*>? {
    val genericInterfaces = formatter.javaClass.genericInterfaces
    var type: Type
    if (genericInterfaces.size == 1) {
        type = genericInterfaces[0]
    } else {
        type = formatter.javaClass.genericSuperclass
    }
    type = (type as ParameterizedType).getActualTypeArguments()[0]
    while (type is ParameterizedType) {
        type = (type as ParameterizedType).getRawType()
    }
    var className = type.toString()
    if (className.startsWith("class ")) {
        className = className.substring(6)
    } else if (className.startsWith("interface ")) {
        className = className.substring(10)
    }
    try {
        return Class.forName(className)
    } catch (e: ClassNotFoundException) {
        e.printStackTrace()
    }

    return null
}

private fun getClassFromObject(obj: Any): Class<*> {
    val objClass = obj.javaClass
    if (objClass.isAnonymousClass || objClass.isSynthetic) {
        val genericInterfaces = objClass.genericInterfaces
        var className: String
        if (genericInterfaces.size == 1) {// interface
            var type = genericInterfaces[0]
            while (type is ParameterizedType) {
                type = (type as ParameterizedType).getRawType()
            }
            className = type.toString()
        } else {// abstract class or lambda
            var type = objClass.genericSuperclass
            while (type is ParameterizedType) {
                type = (type as ParameterizedType).getRawType()
            }
            className = type.toString()
        }

        if (className.startsWith("class ")) {
            className = className.substring(6)
        } else if (className.startsWith("interface ")) {
            className = className.substring(10)
        }
        try {
            return Class.forName(className)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }

    }
    return objClass
}