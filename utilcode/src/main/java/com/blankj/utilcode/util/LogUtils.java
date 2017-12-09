package com.blankj.utilcode.util

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.support.annotation.IntDef
import android.support.annotation.IntRange
import android.util.Log

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.io.StringReader
import java.io.StringWriter
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.text.Format
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Formatter
import java.util.Locale
import java.util.concurrent.Callable
import java.util.concurrent.ExecutionException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

import javax.xml.transform.OutputKeys
import javax.xml.transform.Source
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/09/21
 * desc  : Log 相关工具类
</pre> *
 */
class LogUtils private constructor() {

    @IntDef(V.toLong(), D.toLong(), I.toLong(), W.toLong(), E.toLong(), A.toLong())
    @Retention(RetentionPolicy.SOURCE)
    private annotation class TYPE

    init {
        throw UnsupportedOperationException("u can't instantiate me...")
    }

    class Config private constructor() {
        init {
            if (sDefaultDir != null) return
            if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() && Utils.getApp().externalCacheDir != null)
                sDefaultDir = Utils.getApp().externalCacheDir.toString() + FILE_SEP + "log" + FILE_SEP
            else {
                sDefaultDir = Utils.getApp().cacheDir.toString() + FILE_SEP + "log" + FILE_SEP
            }
        }

        fun setLogSwitch(logSwitch: Boolean): Config {
            sLogSwitch = logSwitch
            return this
        }

        fun setConsoleSwitch(consoleSwitch: Boolean): Config {
            sLog2ConsoleSwitch = consoleSwitch
            return this
        }

        fun setGlobalTag(tag: String): Config {
            if (isSpace(tag)) {
                sGlobalTag = ""
                sTagIsSpace = true
            } else {
                sGlobalTag = tag
                sTagIsSpace = false
            }
            return this
        }

        fun setLogHeadSwitch(logHeadSwitch: Boolean): Config {
            sLogHeadSwitch = logHeadSwitch
            return this
        }

        fun setLog2FileSwitch(log2FileSwitch: Boolean): Config {
            sLog2FileSwitch = log2FileSwitch
            return this
        }

        fun setDir(dir: String): Config {
            if (isSpace(dir)) {
                sDir = null
            } else {
                sDir = if (dir.endsWith(FILE_SEP)) dir else dir + FILE_SEP
            }
            return this
        }

        fun setDir(dir: File?): Config {
            sDir = if (dir == null) null else dir.absolutePath + FILE_SEP
            return this
        }

        fun setFilePrefix(filePrefix: String): Config {
            if (isSpace(filePrefix)) {
                sFilePrefix = "util"
            } else {
                sFilePrefix = filePrefix
            }
            return this
        }

        fun setBorderSwitch(borderSwitch: Boolean): Config {
            sLogBorderSwitch = borderSwitch
            return this
        }

        fun setConsoleFilter(@TYPE consoleFilter: Int): Config {
            sConsoleFilter = consoleFilter
            return this
        }

        fun setFileFilter(@TYPE fileFilter: Int): Config {
            sFileFilter = fileFilter
            return this
        }

        fun setStackDeep(@IntRange(from = 1) stackDeep: Int): Config {
            sStackDeep = stackDeep
            return this
        }

        override fun toString(): String {
            return ("switch: " + sLogSwitch
                    + LINE_SEP + "console: " + sLog2ConsoleSwitch
                    + LINE_SEP + "tag: " + (if (sTagIsSpace) "null" else sGlobalTag)
                    + LINE_SEP + "head: " + sLogHeadSwitch
                    + LINE_SEP + "file: " + sLog2FileSwitch
                    + LINE_SEP + "dir: " + (if (sDir == null) sDefaultDir else sDir)
                    + LINE_SEP + "filePrefix" + sFilePrefix
                    + LINE_SEP + "border: " + sLogBorderSwitch
                    + LINE_SEP + "consoleFilter: " + T[sConsoleFilter - V]
                    + LINE_SEP + "fileFilter: " + T[sFileFilter - V]
                    + LINE_SEP + "stackDeep: " + sStackDeep)
        }
    }

    private class TagHead internal constructor(internal var tag: String, internal var consoleHead: Array<String>, internal var fileHead: String)

    companion object {

        val V = Log.VERBOSE
        val D = Log.DEBUG
        val I = Log.INFO
        val W = Log.WARN
        val E = Log.ERROR
        val A = Log.ASSERT

        private val T = charArrayOf('V', 'D', 'I', 'W', 'E', 'A')

        private val FILE = 0x10
        private val JSON = 0x20
        private val XML = 0x30

        private val FILE_SEP = System.getProperty("file.separator")
        private val LINE_SEP = System.getProperty("line.separator")
        private val TOP_CORNER = "┌"
        private val MIDDLE_CORNER = "├"
        private val LEFT_BORDER = "│ "
        private val BOTTOM_CORNER = "└"
        private val SIDE_DIVIDER = "────────────────────────────────────────────────────────"
        private val MIDDLE_DIVIDER = "┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄"
        private val TOP_BORDER = TOP_CORNER + SIDE_DIVIDER + SIDE_DIVIDER
        private val MIDDLE_BORDER = MIDDLE_CORNER + MIDDLE_DIVIDER + MIDDLE_DIVIDER
        private val BOTTOM_BORDER = BOTTOM_CORNER + SIDE_DIVIDER + SIDE_DIVIDER
        private val MAX_LEN = 4000
        private val FORMAT = SimpleDateFormat("MM-dd HH:mm:ss.SSS ", Locale.getDefault())
        private val NULL = "null"
        private val ARGS = "args"
        val config = Config()

        private var sExecutor: ExecutorService? = null
        private var sDefaultDir: String? = null// log 默认存储目录
        private var sDir: String? = null       // log 存储目录
        private var sFilePrefix = "util"// log 文件前缀
        private var sLogSwitch = true  // log 总开关，默认开
        private var sLog2ConsoleSwitch = true  // logcat 是否打印，默认打印
        private var sGlobalTag: String? = null  // log 标签
        private var sTagIsSpace = true  // log 标签是否为空白
        private var sLogHeadSwitch = true  // log 头部开关，默认开
        private var sLog2FileSwitch = false // log 写入文件开关，默认关
        private var sLogBorderSwitch = true  // log 边框开关，默认开
        private var sConsoleFilter = V     // log 控制台过滤器
        private var sFileFilter = V     // log 文件过滤器
        private var sStackDeep = 1     // log 栈深度

        fun v(vararg contents: Any) {
            log(V, sGlobalTag, *contents)
        }

        fun vTag(tag: String, vararg contents: Any) {
            log(V, tag, *contents)
        }

        fun d(vararg contents: Any) {
            log(D, sGlobalTag, *contents)
        }

        fun dTag(tag: String, vararg contents: Any) {
            log(D, tag, *contents)
        }

        fun i(vararg contents: Any) {
            log(I, sGlobalTag, *contents)
        }

        fun iTag(tag: String, vararg contents: Any) {
            log(I, tag, *contents)
        }

        fun w(vararg contents: Any) {
            log(W, sGlobalTag, *contents)
        }

        fun wTag(tag: String, vararg contents: Any) {
            log(W, tag, *contents)
        }

        fun e(vararg contents: Any) {
            log(E, sGlobalTag, *contents)
        }

        fun eTag(tag: String, vararg contents: Any) {
            log(E, tag, *contents)
        }

        fun a(vararg contents: Any) {
            log(A, sGlobalTag, *contents)
        }

        fun aTag(tag: String, vararg contents: Any) {
            log(A, tag, *contents)
        }

        fun file(content: Any) {
            log(FILE or D, sGlobalTag, content)
        }

        fun file(@TYPE type: Int, content: Any) {
            log(FILE or type, sGlobalTag, content)
        }

        fun file(tag: String, content: Any) {
            log(FILE or D, tag, content)
        }

        fun file(@TYPE type: Int, tag: String, content: Any) {
            log(FILE or type, tag, content)
        }

        fun json(content: String) {
            log(JSON or D, sGlobalTag, content)
        }

        fun json(@TYPE type: Int, content: String) {
            log(JSON or type, sGlobalTag, content)
        }

        fun json(tag: String, content: String) {
            log(JSON or D, tag, content)
        }

        fun json(@TYPE type: Int, tag: String, content: String) {
            log(JSON or type, tag, content)
        }

        fun xml(content: String) {
            log(XML or D, sGlobalTag, content)
        }

        fun xml(@TYPE type: Int, content: String) {
            log(XML or type, sGlobalTag, content)
        }

        fun xml(tag: String, content: String) {
            log(XML or D, tag, content)
        }

        fun xml(@TYPE type: Int, tag: String, content: String) {
            log(XML or type, tag, content)
        }

        private fun log(type: Int, tag: String?, vararg contents: Any) {
            if (!sLogSwitch || !sLog2ConsoleSwitch && !sLog2FileSwitch) return
            val type_low = type and 0x0f
            val type_high = type and 0xf0
            if (type_low < sConsoleFilter && type_low < sFileFilter) return
            val tagHead = processTagAndHead(tag)
            val body = processBody(type_high, *contents)
            if (sLog2ConsoleSwitch && type_low >= sConsoleFilter && type_high != FILE) {
                print2Console(type_low, tagHead.tag, tagHead.consoleHead, body)
            }
            if ((sLog2FileSwitch || type_high == FILE) && type_low >= sFileFilter) {
                print2File(type_low, tagHead.tag, tagHead.fileHead + body)
            }
        }

        private fun processTagAndHead(tag: String?): TagHead {
            var tag = tag
            if (!sTagIsSpace && !sLogHeadSwitch) {
                tag = sGlobalTag
            } else {
                val stackTrace = Throwable().stackTrace
                var targetElement = stackTrace[3]
                var fileName: String? = targetElement.fileName
                var className: String
                if (fileName == null) {// 混淆可能会导致获取为空 加-keepattributes SourceFile,LineNumberTable
                    className = targetElement.className
                    val classNameInfo = className.split("\\.".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
                    if (classNameInfo.size > 0) {
                        className = classNameInfo[classNameInfo.size - 1]
                    }
                    val index = className.indexOf('$')
                    if (index != -1) {
                        className = className.substring(0, index)
                    }
                    fileName = className + ".java"
                } else {
                    val index = fileName.indexOf('.')// 混淆可能导致文件名被改变从而找不到"."
                    className = if (index == -1) fileName else fileName.substring(0, index)
                }
                if (sTagIsSpace) tag = if (isSpace(tag)) className else tag
                if (sLogHeadSwitch) {
                    val tName = Thread.currentThread().name
                    val head = Formatter()
                            .format("%s, %s(%s:%d)",
                                    tName,
                                    targetElement.methodName,
                                    fileName,
                                    targetElement.lineNumber)
                            .toString()
                    val fileHead = " [$head]: "
                    if (sStackDeep <= 1) {
                        return TagHead(tag, arrayOf(head), fileHead)
                    } else {
                        val consoleHead = arrayOfNulls<String>(Math.min(sStackDeep, stackTrace.size - 3))
                        consoleHead[0] = head
                        val spaceLen = tName.length + 2
                        val space = Formatter().format("%" + spaceLen + "s", "").toString()
                        var i = 1
                        val len = consoleHead.size
                        while (i < len) {
                            targetElement = stackTrace[i + 3]
                            consoleHead[i] = Formatter()
                                    .format("%s%s(%s:%d)",
                                            space,
                                            targetElement.methodName,
                                            targetElement.fileName,
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

        private fun processBody(type: Int, vararg contents: Any): String {
            var body = NULL
            if (contents != null) {
                if (contents.size == 1) {
                    val `object` = contents[0]
                    if (`object` != null) body = `object`.toString()
                    if (type == JSON) {
                        body = formatJson(body)
                    } else if (type == XML) {
                        body = formatXml(body)
                    }
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
                                .append(content?.toString() ?: NULL)
                                .append(LINE_SEP)
                        ++i
                    }
                    body = sb.toString()
                }
            }
            return body
        }

        private fun formatJson(json: String): String {
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

        private fun formatXml(xml: String): String {
            var xml = xml
            try {
                val xmlInput = StreamSource(StringReader(xml))
                val xmlOutput = StreamResult(StringWriter())
                val transformer = TransformerFactory.newInstance().newTransformer()
                transformer.setOutputProperty(OutputKeys.INDENT, "yes")
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4")
                transformer.transform(xmlInput, xmlOutput)
                xml = xmlOutput.writer.toString().replaceFirst(">".toRegex(), ">" + LINE_SEP)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return xml
        }

        private fun print2Console(type: Int, tag: String, head: Array<String>, msg: String) {
            printBorder(type, tag, true)
            printHead(type, tag, head)
            printMsg(type, tag, msg)
            printBorder(type, tag, false)
        }

        private fun printBorder(type: Int, tag: String, isTop: Boolean) {
            if (sLogBorderSwitch) {
                Log.println(type, tag, if (isTop) TOP_BORDER else BOTTOM_BORDER)
            }
        }

        private fun printHead(type: Int, tag: String, head: Array<String>?) {
            if (head != null) {
                for (aHead in head) {
                    Log.println(type, tag, if (sLogBorderSwitch) LEFT_BORDER + aHead else aHead)
                }
                if (sLogBorderSwitch) Log.println(type, tag, MIDDLE_BORDER)
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
            if (!sLogBorderSwitch) {
                Log.println(type, tag, msg)
                return
            }
            val sb = StringBuilder()
            val lines = msg.split(LINE_SEP.toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
            for (line in lines) {
                Log.println(type, tag, LEFT_BORDER + line)
            }
        }

        private fun print2File(type: Int, tag: String, msg: String) {
            val now = Date(System.currentTimeMillis())
            val format = FORMAT.format(now)
            val date = format.substring(0, 5)
            val time = format.substring(6)
            val fullPath = (if (sDir == null) sDefaultDir else sDir) + sFilePrefix + "-" + date + ".txt"
            if (!createOrExistsFile(fullPath)) {
                Log.e(tag, "log to $fullPath failed!")
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
            if (input2File(content, fullPath)) {
                Log.d(tag, "log to $fullPath success!")
            } else {
                Log.e(tag, "log to $fullPath failed!")
            }
        }

        private fun createOrExistsFile(filePath: String): Boolean {
            val file = File(filePath)
            if (file.exists()) return file.isFile
            if (!createOrExistsDir(file.parentFile)) return false
            try {
                val isCreate = file.createNewFile()
                if (isCreate) printDeviceInfo(filePath)
                return isCreate
            } catch (e: IOException) {
                e.printStackTrace()
                return false
            }

        }

        private fun printDeviceInfo(filePath: String) {
            var versionName = ""
            var versionCode = 0
            try {
                val pi = Utils.getApp().packageManager.getPackageInfo(Utils.getApp().packageName, 0)
                if (pi != null) {
                    versionName = pi.versionName
                    versionCode = pi.versionCode
                }
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }

            val head = "************* Log Head ****************" +
                    "\nDevice Manufacturer: " + Build.MANUFACTURER +// 设备厂商

                    "\nDevice Model       : " + Build.MODEL +// 设备型号

                    "\nAndroid Version    : " + Build.VERSION.RELEASE +// 系统版本

                    "\nAndroid SDK        : " + Build.VERSION.SDK_INT +// SDK 版本

                    "\nApp VersionName    : " + versionName +
                    "\nApp VersionCode    : " + versionCode +
                    "\n************* Log Head ****************\n\n"
            input2File(head, filePath)
        }

        private fun createOrExistsDir(file: File?): Boolean {
            return file != null && if (file.exists()) file.isDirectory else file.mkdirs()
        }

        private fun isSpace(s: String?): Boolean {
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

        private fun input2File(input: String, filePath: String): Boolean {
            if (sExecutor == null) {
                sExecutor = Executors.newSingleThreadExecutor()
            }
            val submit = sExecutor!!.submit(Callable {
                var bw: BufferedWriter? = null
                try {
                    bw = BufferedWriter(FileWriter(filePath, true))
                    bw.write(input)
                    return@Callable true
                } catch (e: IOException) {
                    e.printStackTrace()
                    return@Callable false
                } finally {
                    try {
                        if (bw != null) {
                            bw.close()
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
            })
            try {
                return submit.get()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            } catch (e: ExecutionException) {
                e.printStackTrace()
            }

            return false
        }
    }
}
