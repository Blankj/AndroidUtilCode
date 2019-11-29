package com.blankj.utilcode.pkg.feature.log

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.blankj.base.BaseApplication
import com.blankj.common.activity.CommonActivity
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemClick
import com.blankj.common.item.CommonItemSwitch
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.*
import java.io.File
import java.util.*


/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2017/03/22
 * desc  : demo about LogUtils
 * ```
 */
class LogActivity : CommonActivity() {

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
            for (i in 0..1024) {
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


    override fun bindTitleRes(): Int {
        return R.string.demo_log
    }

    override fun bindItems(): List<CommonItem<*>> {
        return CollectionUtils.newArrayList(
                CommonItemSwitch(
                        R.string.log_switch,
                        Utils.Func1 {
                            mConfig.isLogSwitch
                        },
                        Utils.Func1 {
                            mConfig.isLogSwitch = it
                        }
                ),
                CommonItemSwitch(
                        R.string.log_console_console,
                        Utils.Func1 {
                            mConfig.isLog2ConsoleSwitch
                        },
                        Utils.Func1 {
                            mConfig.setConsoleSwitch(it)
                        }
                ),
                CommonItemClick("Global Tag", if (mConfig.globalTag == "") "\"\"" else mConfig.globalTag).setOnClickUpdateContentListener {
                    if (StringUtils.isSpace(mConfig.globalTag)) {
                        mConfig.globalTag = TAG
                    } else {
                        mConfig.globalTag = ""
                    }
                    return@setOnClickUpdateContentListener if (mConfig.globalTag == "") "\"\"" else mConfig.globalTag
                },
                CommonItemSwitch(
                        R.string.log_head_switch,
                        Utils.Func1 {
                            mConfig.isLogHeadSwitch
                        },
                        Utils.Func1 {
                            mConfig.isLogHeadSwitch = it
                        }
                ),
                CommonItemSwitch(
                        R.string.log_file_switch,
                        Utils.Func1 {
                            mConfig.isLog2FileSwitch
                        },
                        Utils.Func1 {
                            mConfig.isLog2FileSwitch = it
                        }
                ),
                CommonItemClick("Dir", mConfig.dir).setOnClickUpdateContentListener {
                    if (mConfig.dir != mConfig.defaultDir) {
                        mConfig.dir = mConfig.defaultDir
                    } else {
                        mConfig.setDir(File(PathUtils.getInternalAppFilesPath(), "log"))
                    }
                    return@setOnClickUpdateContentListener mConfig.dir
                },
                CommonItemSwitch(
                        R.string.log_border_switch,
                        Utils.Func1 {
                            mConfig.isLogBorderSwitch
                        },
                        Utils.Func1 {
                            mConfig.setBorderSwitch(it)
                        }
                ),
                CommonItemSwitch(
                        R.string.log_single_tag_switch,
                        Utils.Func1 {
                            mConfig.isSingleTagSwitch
                        },
                        Utils.Func1 {
                            mConfig.setSingleTagSwitch(it)
                        }
                ),
                CommonItemClick("ConsoleFilter", mConfig.consoleFilter.toString()).setOnClickUpdateContentListener {
                    mConfig.setConsoleFilter(if (mConfig.consoleFilter == 'V') LogUtils.W else LogUtils.V)
                    return@setOnClickUpdateContentListener mConfig.consoleFilter.toString()
                },
                CommonItemClick("FileFilter", mConfig.fileFilter.toString()).setOnClickUpdateContentListener {
                    mConfig.setFileFilter(if (mConfig.fileFilter == 'V') LogUtils.W else LogUtils.V)
                    return@setOnClickUpdateContentListener mConfig.fileFilter.toString()
                },
                CommonItemClick(R.string.log_with_no_tag) {
                    LogUtils.v("verbose")
                    LogUtils.d("debug")
                    LogUtils.i("info")
                    LogUtils.w("warn")
                    LogUtils.e("error")
                    LogUtils.a("assert")
                },
                CommonItemClick(R.string.log_with_tag) {
                    LogUtils.vTag("customTag", "verbose")
                    LogUtils.dTag("customTag", "debug")
                    LogUtils.iTag("customTag", "info")
                    LogUtils.wTag("customTag", "warn")
                    LogUtils.eTag("customTag", "error")
                    LogUtils.aTag("customTag", "assert")
                },
                CommonItemClick(R.string.log_in_new_thread) {
                    val thread = Thread(mRunnable)
                    thread.start()
                },
                CommonItemClick(R.string.log_null) {
                    LogUtils.v(null)
                    LogUtils.d(null)
                    LogUtils.i(null)
                    LogUtils.w(null)
                    LogUtils.e(null)
                    LogUtils.a(null)
                },
                CommonItemClick(R.string.log_many_params) {
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
                },
                CommonItemClick(R.string.log_long_string) {
                    LogUtils.d(LONG_STR)
                },
                CommonItemClick(R.string.log_to_file) {
                    LogUtils.file("test0 log to file")
                    LogUtils.file(LogUtils.I, "test0 log to file")
                },
                CommonItemClick(R.string.log_json) {
                    LogUtils.json(JSON)
                    LogUtils.json(LogUtils.I, JSON)
                },
                CommonItemClick(R.string.log_xml) {
                    LogUtils.xml(XML)
                    LogUtils.xml(LogUtils.I, XML)
                },
                CommonItemClick(R.string.log_array) {
                    LogUtils.e(ONE_D_ARRAY)
                    LogUtils.e(TWO_D_ARRAY)
                },
                CommonItemClick(R.string.log_throwable) {
                    LogUtils.e(THROWABLE)
                },
                CommonItemClick(R.string.log_bundle) {
                    LogUtils.e(BUNDLE)
                },
                CommonItemClick(R.string.log_intent) {
                    LogUtils.e(INTENT)
                },
                CommonItemClick(R.string.log_array_list) {
                    LogUtils.e(LIST)
                },
                CommonItemClick(R.string.log_map) {
                    LogUtils.e(MAP)
                }
        )
    }

    override fun onDestroy() {
        BaseApplication.getInstance().initLog()
        super.onDestroy()
    }
}
