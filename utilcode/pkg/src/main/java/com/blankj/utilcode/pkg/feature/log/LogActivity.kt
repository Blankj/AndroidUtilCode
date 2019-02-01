package com.blankj.utilcode.pkg.feature.log

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import com.blankj.lib.base.BaseApplication
import com.blankj.lib.base.BaseTitleBarActivity
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.PathUtils
import com.blankj.utilcode.util.StringUtils
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
class LogActivity : BaseTitleBarActivity(),
        CompoundButton.OnCheckedChangeListener {

    companion object {
        private const val TAG = "CMJ"
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

    private val mRunnable = Runnable {
        LogUtils.v("verbose")
        LogUtils.d("debug")
        LogUtils.i("info")
        LogUtils.w("warn")
        LogUtils.e("error")
        LogUtils.a("assert")
    }

    override fun bindTitle(): CharSequence {
        return getString(R.string.demo_log)
    }

    override fun initData(bundle: Bundle?) {}

    override fun bindLayout(): Int {
        return R.layout.activity_log
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        logSwitchCb.isChecked = mConfig.isLogSwitch
        logSwitchCb.setOnCheckedChangeListener(this)

        log2ConsoleSwitchCb.isChecked = mConfig.isLog2ConsoleSwitch
        log2ConsoleSwitchCb.setOnCheckedChangeListener(this)

        logGlobalTagCb.isChecked = !StringUtils.isSpace(mConfig.globalTag)
        logGlobalTagCb.setOnCheckedChangeListener(this)
        logGlobalTagCb.text = String.format("Global Tag: %s", mConfig.globalTag)

        logHeadSwitchCb.isChecked = mConfig.isLogHeadSwitch
        logHeadSwitchCb.setOnCheckedChangeListener(this)

        log2FileSwitchCb.isChecked = mConfig.isLog2FileSwitch
        log2FileSwitchCb.setOnCheckedChangeListener(this)

        logDirCb.isChecked = mConfig.dir != mConfig.defaultDir
        logDirCb.setOnCheckedChangeListener(this)
        logDirCb.text = String.format("Dir: %s", mConfig.dir)

        logBorderSwitchCb.isChecked = mConfig.isLogBorderSwitch
        logBorderSwitchCb.setOnCheckedChangeListener(this)

        logBorderSwitchCb.isChecked = mConfig.isSingleTagSwitch
        logSingleTagSwitchCb.setOnCheckedChangeListener(this)

        logConsoleFilterCb.isChecked = mConfig.consoleFilter != 'V'
        logConsoleFilterCb.setOnCheckedChangeListener(this)
        logConsoleFilterCb.text = String.format("ConsoleFilter: %s", mConfig.consoleFilter)

        logFileFilterCb.isChecked = mConfig.fileFilter != 'V'
        logFileFilterCb.setOnCheckedChangeListener(this)
        logFileFilterCb.text = String.format("FileFilter: %s", mConfig.fileFilter)

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
        updateAboutLog()
    }

    override fun doBusiness() {}

    override fun onWidgetClick(view: View) {
        when (view.id) {
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

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        when (buttonView?.id) {
            R.id.logSwitchCb -> mConfig.setLogSwitch(isChecked)
            R.id.log2ConsoleSwitchCb -> mConfig.setConsoleSwitch(isChecked)
            R.id.logGlobalTagCb -> {
                if (isChecked) {
                    mConfig.setGlobalTag(TAG)
                } else {
                    mConfig.setGlobalTag("")
                }
                logGlobalTagCb.text = String.format("Global Tag: %s", mConfig.globalTag)
            }
            R.id.logHeadSwitchCb -> mConfig.setLogHeadSwitch(isChecked)
            R.id.log2FileSwitchCb -> mConfig.setLog2FileSwitch(isChecked)
            R.id.logDirCb -> {
                if (isChecked) {
                    mConfig.setDir("")
                } else {
                    mConfig.setDir(PathUtils.getInternalAppFilesPath() + System.getProperty("file.separator") + "test")
                }
                logDirCb.text = String.format("Dir: %s", mConfig.dir)
            }
            R.id.logBorderSwitchCb -> mConfig.setBorderSwitch(isChecked)
            R.id.logSingleTagSwitchCb -> mConfig.setSingleTagSwitch(isChecked)
            R.id.logConsoleFilterCb -> {
                mConfig.setConsoleFilter(if (isChecked) LogUtils.W else LogUtils.V)
                logConsoleFilterCb.text = String.format("ConsoleFilter: %s", mConfig.consoleFilter)
            }
            R.id.logFileFilterCb -> {
                mConfig.setFileFilter(if (isChecked) LogUtils.W else LogUtils.V)
                logFileFilterCb.text = String.format("FileFilter: %s", mConfig.fileFilter)
            }
        }
        updateAboutLog();
    }

    private fun updateAboutLog() {
        logAboutTv.text = mConfig.toString()
    }

    override fun onDestroy() {
        BaseApplication.instance.initLog()
        super.onDestroy()
    }
}
