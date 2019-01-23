package com.blankj.utilcode.pkg.feature.log

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.blankj.lib.base.BaseApplication
import com.blankj.lib.base.BaseBackActivity
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.PathUtils
import kotlinx.android.synthetic.main.activity_log.*
import java.util.*


/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2017/03/22
 * desc  : demo about LogUtils
 * ```
 */
class LogActivity : BaseBackActivity() {

    companion object {
        private const val TAG = "CMJ"
        private const val UPDATE_LOG = 0x01
        private const val UPDATE_CONSOLE = 0x01 shl 1
        private const val UPDATE_TAG = 0x01 shl 2
        private const val UPDATE_HEAD = 0x01 shl 3
        private const val UPDATE_FILE = 0x01 shl 4
        private const val UPDATE_DIR = 0x01 shl 5
        private const val UPDATE_BORDER = 0x01 shl 6
        private const val UPDATE_SINGLE = 0x01 shl 7
        private const val UPDATE_CONSOLE_FILTER = 0x01 shl 8
        private const val UPDATE_FILE_FILTER = 0x01 shl 9

        private const val JSON = "{\"tools\": [{ \"name\":\"css format\" , \"site\":\"http://tools.w3cschool.cn/code/css\" },{ \"name\":\"JSON format\" , \"site\":\"http://tools.w3cschool.cn/code/JSON\" },{ \"name\":\"pwd check\" , \"site\":\"http://tools.w3cschool.cn/password/my_password_safe\" }]}"
        private const val XML = "<books><book><author>Jack Herrington</author><title>PHP Hacks</title><publisher>O'Reilly</publisher></book><book><author>Jack Herrington</author><title>Podcasting Hacks</title><publisher>O'Reilly</publisher></book></books>"
        private val ONE_D_ARRAY = intArrayOf(1, 2, 3)
        private val TWO_D_ARRAY = arrayOf(intArrayOf(1, 2, 3), intArrayOf(4, 5, 6), intArrayOf(7, 8, 9))
        private val THROWABLE = NullPointerException()
        private val BUNDLE = Bundle()
        private val INTENT = Intent()
        private val LIST = ArrayList<String>()
        private val MAP = HashMap<String, String>()

        private val LONG_STR: String

        init {
            val sb = StringBuilder()
            sb.append("len = 10400\ncontent = \"")
            for (i in 0..799) {
                sb.append("Hello world. ")
            }
            sb.append("\"")
            LONG_STR = sb.toString()

            BUNDLE.putByte("byte", (-1).toByte())
            BUNDLE.putChar("char", 'c')
            BUNDLE.putCharArray("charArray", charArrayOf('c', 'h', 'a', 'r', 'A', 'r', 'r', 'a', 'y'))
            BUNDLE.putCharSequence("charSequence", "charSequence")
            BUNDLE.putCharSequenceArray("charSequenceArray", arrayOf<CharSequence>("char", "Sequence", "Array"))
            BUNDLE.putBundle("bundle", BUNDLE)
            BUNDLE.putBoolean("boolean", true)
            BUNDLE.putInt("int", 1)
            BUNDLE.putFloat("float", 1f)
            BUNDLE.putLong("long", 1L)
            BUNDLE.putShort("short", 1.toShort())

            INTENT.action = "LogUtils action"
            INTENT.addCategory("LogUtils category")
            INTENT.data = Uri.parse("intent data")
            INTENT.type = "intent type"
            INTENT.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            INTENT.setPackage(AppUtils.getAppPackageName())
            INTENT.component = ComponentName(AppUtils.getAppPackageName(), LogActivity::class.java.toString())
            INTENT.putExtra("int", 1)
            INTENT.putExtra("float", 1f)
            INTENT.putExtra("char", 'c')
            INTENT.putExtra("string", "string")
            INTENT.putExtra("intArray", ONE_D_ARRAY)
            val list = ArrayList<String>()
            list.add("ArrayList")
            list.add("is")
            list.add("serializable")
            INTENT.putExtra("serializable", list)
            INTENT.putExtra("bundle", BUNDLE)

            LIST.add("hello")
            LIST.add("log")
            LIST.add("utils")

            MAP["name"] = "AndroidUtilCode"
            MAP["class"] = "LogUtils"
        }

        fun start(context: Context) {
            val starter = Intent(context, LogActivity::class.java)
            context.startActivity(starter)
        }
    }

    private val mConfig = LogUtils.getConfig()

    private var dir: String = ""
    private var globalTag = ""
    private var log = true
    private var console = true
    private var head = true
    private var file = false
    private var border = true
    private var single = true
    private var consoleFilter = LogUtils.V
    private var fileFilter = LogUtils.V

    private val mRunnable = Runnable {
        LogUtils.v("verbose")
        LogUtils.d("debug")
        LogUtils.i("info")
        LogUtils.w("warn")
        LogUtils.e("error")
        LogUtils.a("assert")
    }

    override fun initData(bundle: Bundle?) {

    }

    override fun bindLayout(): Int {
        return R.layout.activity_log
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        setTitle(R.string.demo_log)

        logToggleLogBtn.setOnClickListener(this)
        logToggleConsoleBtn.setOnClickListener(this)
        logToggleTagBtn.setOnClickListener(this)
        logToggleHeadBtn.setOnClickListener(this)
        logToggleBorderBtn.setOnClickListener(this)
        logToggleSingleBtn.setOnClickListener(this)
        logToggleFileBtn.setOnClickListener(this)
        logToggleDirBtn.setOnClickListener(this)
        logToggleConsoleFilterBtn.setOnClickListener(this)
        logToggleFileFilterBtn.setOnClickListener(this)
        logNoTagBtn.setOnClickListener(this)
        logWithTagBtn.setOnClickListener(this)
        logInNewThreadBtn.setOnClickListener(this)
        logNullBtn.setOnClickListener(this)
        logManyParamsBtn.setOnClickListener(this)
        logLongBtn.setOnClickListener(this)
        logFileBtn.setOnClickListener(this)
        logJsonBtn.setOnClickListener(this)
        logXmlBtn.setOnClickListener(this)
        logArrayBtn.setOnClickListener(this)
        logThrowableBtn.setOnClickListener(this)
        logBundleBtn.setOnClickListener(this)
        logIntentBtn.setOnClickListener(this)
        logArrayListBtn.setOnClickListener(this)
        logMapBtn.setOnClickListener(this)
        updateConfig(0)
    }

    override fun doBusiness() {

    }

    override fun onWidgetClick(view: View) {
        when (view.id) {
            R.id.logToggleLogBtn -> updateConfig(UPDATE_LOG)
            R.id.logToggleConsoleBtn -> updateConfig(UPDATE_CONSOLE)
            R.id.logToggleTagBtn -> updateConfig(UPDATE_TAG)
            R.id.logToggleHeadBtn -> updateConfig(UPDATE_HEAD)
            R.id.logToggleFileBtn -> updateConfig(UPDATE_FILE)
            R.id.logToggleDirBtn -> updateConfig(UPDATE_DIR)
            R.id.logToggleBorderBtn -> updateConfig(UPDATE_BORDER)
            R.id.logToggleSingleBtn -> updateConfig(UPDATE_SINGLE)
            R.id.logToggleConsoleFilterBtn -> updateConfig(UPDATE_CONSOLE_FILTER)
            R.id.logToggleFileFilterBtn -> updateConfig(UPDATE_FILE_FILTER)
            R.id.logNoTagBtn -> {
                LogUtils.v("verbose")
                LogUtils.d("debug")
                LogUtils.i("info")
                LogUtils.w("warn")
                LogUtils.e("error")
                LogUtils.a("assert")

            }
            R.id.logWithTagBtn -> {
                LogUtils.vTag("customTag", "verbose")
                LogUtils.dTag("customTag", "debug")
                LogUtils.iTag("customTag", "info")
                LogUtils.wTag("customTag", "warn")
                LogUtils.eTag("customTag", "error")
                LogUtils.aTag("customTag", "assert")

            }
            R.id.logInNewThreadBtn -> {
                val thread = Thread(mRunnable)
                thread.start()

            }
            R.id.logNullBtn -> {
                LogUtils.v(null)
                LogUtils.d(null)
                LogUtils.i(null)
                LogUtils.w(null)
                LogUtils.e(null)
                LogUtils.a(null)

            }
            R.id.logManyParamsBtn -> {
                LogUtils.v("verbose0", "verbose1")
                LogUtils.vTag("customTag", "verbose0", "verbose1")
                LogUtils.d("debug0", "debug1")
                LogUtils.dTag("customTag", "debug0", "debug1")
                LogUtils.i("info0", "info1")
                LogUtils.iTag("customTag", "info0", "info1")
                LogUtils.w("warn0", "warn1")
                LogUtils.wTag("customTag", "warn0", "warn1")
                LogUtils.e("error0", "error1")
                LogUtils.eTag("customTag", "error0", "error1")
                LogUtils.a("assert0", "assert1")
                LogUtils.aTag("customTag", "assert0", "assert1")

            }
            R.id.logLongBtn -> LogUtils.d(LONG_STR)
            R.id.logFileBtn -> for (i in 0..99) {
                LogUtils.file("test0 log to file")
                LogUtils.file(LogUtils.I, "test0 log to file")
            }
            R.id.logJsonBtn -> {
                LogUtils.json(JSON)
                LogUtils.json(LogUtils.I, JSON)

            }
            R.id.logXmlBtn -> {
                LogUtils.xml(XML)
                LogUtils.xml(LogUtils.I, XML)

            }
            R.id.logArrayBtn -> {
                LogUtils.e(ONE_D_ARRAY)
                LogUtils.e(TWO_D_ARRAY)

            }
            R.id.logThrowableBtn -> LogUtils.e(THROWABLE)
            R.id.logBundleBtn -> LogUtils.e(BUNDLE)
            R.id.logIntentBtn -> LogUtils.e(INTENT)
            R.id.logArrayListBtn -> LogUtils.e(LIST)
            R.id.logMapBtn -> LogUtils.e(MAP)
        }
    }

    private fun updateConfig(args: Int) {
        when (args) {
            UPDATE_LOG -> log = !log
            UPDATE_CONSOLE -> console = !console
            UPDATE_TAG -> globalTag = if (globalTag == TAG) "" else TAG
            UPDATE_HEAD -> head = !head
            UPDATE_FILE -> file = !file
            UPDATE_DIR -> dir =
                    if (getDir().contains("test")) {
                        ""
                    } else {
                        PathUtils.getInternalAppFilesPath() + System.getProperty("file.separator") + "test"
                    }
            UPDATE_BORDER -> border = !border
            UPDATE_SINGLE -> single = !single
            UPDATE_CONSOLE_FILTER -> consoleFilter = if (consoleFilter == LogUtils.V) LogUtils.W else LogUtils.V
            UPDATE_FILE_FILTER -> fileFilter = if (fileFilter == LogUtils.V) LogUtils.I else LogUtils.V
        }
        mConfig.setLogSwitch(log)
                .setConsoleSwitch(console)
                .setGlobalTag(globalTag)
                .setLogHeadSwitch(head)
                .setLog2FileSwitch(file)
                .setDir(dir)
                .setBorderSwitch(border)
                .setSingleTagSwitch(single)
                .setConsoleFilter(consoleFilter)
                .setFileFilter(fileFilter)
        logAboutTv.text = mConfig.toString()
    }

    private fun getDir(): String {
        return mConfig.toString()
                .split(System.getProperty("line.separator"))[5]
                .substring(5)
    }

    override fun onDestroy() {
        BaseApplication.instance.initLog()
        super.onDestroy()
    }
}
