package com.blankj.utilcode.pkg.helper

import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.drawable.ColorDrawable
import android.support.v4.app.FragmentActivity
import android.text.method.ScrollingMovementMethod
import android.util.Pair
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.blankj.base.dialog.BaseDialogFragment
import com.blankj.base.dialog.DialogLayoutCallback
import com.blankj.common.dialog.CommonDialogContent
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.*
import com.blankj.utilcode.util.PermissionUtils.OnRationaleListener.ShouldRequest

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2018/01/10
 * desc  : helper about dialog
 * ```
 */
object DialogHelper {

    fun showRationaleDialog(shouldRequest: ShouldRequest) {
        val topActivity = ActivityUtils.getTopActivity() ?: return
        CommonDialogContent().init(topActivity as FragmentActivity,
                StringUtils.getString(android.R.string.dialog_alert_title),
                StringUtils.getString(R.string.permission_rationale_message),
                Pair(StringUtils.getString(android.R.string.ok), View.OnClickListener {
                    shouldRequest.again(true)
                }),
                Pair(StringUtils.getString(android.R.string.cancel), View.OnClickListener {
                    shouldRequest.again(false)
                })).show()
    }

    fun showOpenAppSettingDialog() {
        val topActivity = ActivityUtils.getTopActivity() ?: return
        CommonDialogContent().init(topActivity as FragmentActivity,
                StringUtils.getString(android.R.string.dialog_alert_title),
                StringUtils.getString(R.string.permission_denied_forever_message),
                Pair(StringUtils.getString(android.R.string.ok), View.OnClickListener {
                    PermissionUtils.launchAppDetailsSettings()
                }),
                Pair(StringUtils.getString(android.R.string.cancel), View.OnClickListener {
                }))
                .show()
    }

    fun showKeyboardDialog() {
        val topActivity = ActivityUtils.getTopActivity() ?: return
        BaseDialogFragment().init(topActivity as FragmentActivity, object : DialogLayoutCallback {
            override fun bindTheme(): Int {
                return View.NO_ID
            }

            override fun bindLayout(): Int {
                return R.layout.keyboard_dialog
            }

            override fun initView(dialog: BaseDialogFragment, contentView: View) {
                dialog.dialog.setCanceledOnTouchOutside(false)

                val keyboardDialogEt = contentView.findViewById<EditText>(R.id.keyboardDialogEt)
                val listener = View.OnClickListener { v ->
                    when (v.id) {
                        R.id.keyboardDialogHideSoftInputBtn -> KeyboardUtils.hideSoftInput(keyboardDialogEt)
                        R.id.keyboardDialogShowSoftInputBtn -> KeyboardUtils.showSoftInput(keyboardDialogEt)
                        R.id.keyboardDialogToggleSoftInputBtn -> KeyboardUtils.toggleSoftInput()
                        R.id.keyboardDialogCloseBtn -> {
                            KeyboardUtils.hideSoftInput(keyboardDialogEt)
                            dialog.dismiss()
                        }
                    }
                }
                contentView.findViewById<View>(R.id.keyboardDialogHideSoftInputBtn).setOnClickListener(listener)
                contentView.findViewById<View>(R.id.keyboardDialogShowSoftInputBtn).setOnClickListener(listener)
                contentView.findViewById<View>(R.id.keyboardDialogToggleSoftInputBtn).setOnClickListener(listener)
                contentView.findViewById<View>(R.id.keyboardDialogCloseBtn).setOnClickListener(listener)

                dialog.dialog.setOnShowListener(DialogInterface.OnShowListener {
                    KeyboardUtils.fixAndroidBug5497(dialog.dialog.window)
                })
            }

            override fun setWindowStyle(window: Window) {
                window.setBackgroundDrawable(ColorDrawable(0))
                val attributes = window.attributes
                attributes.gravity = Gravity.BOTTOM
                attributes.width = ScreenUtils.getAppScreenWidth()
                attributes.height = ScreenUtils.getAppScreenHeight() * 2 / 5
                attributes.windowAnimations = R.style.BottomDialogAnimation
                window.attributes = attributes
            }

            override fun onCancel(dialog: BaseDialogFragment) {}

            override fun onDismiss(dialog: BaseDialogFragment) {}
        }).show()
    }

    fun showFragmentDialog(info: CharSequence) {
        val topActivity = ActivityUtils.getTopActivity() ?: return
        BaseDialogFragment().init(topActivity as FragmentActivity, object : DialogLayoutCallback {
            override fun bindTheme(): Int {
                return R.style.CommonContentDialogStyle
            }

            override fun bindLayout(): Int {
                return R.layout.fragment_dialog
            }

            override fun initView(dialog: BaseDialogFragment, contentView: View) {
                val aboutTv = contentView.findViewById<TextView>(R.id.fragmentDialogAboutTv)
                aboutTv.movementMethod = ScrollingMovementMethod.getInstance()
                aboutTv.text = info
            }

            override fun setWindowStyle(window: Window) {}

            override fun onCancel(dialog: BaseDialogFragment) {}

            override fun onDismiss(dialog: BaseDialogFragment) {}
        }).show()
    }

    fun showScreenshotDialog(screenshot: Bitmap) {
        val topActivity = ActivityUtils.getTopActivity() ?: return
        BaseDialogFragment().init(topActivity as FragmentActivity, object : DialogLayoutCallback {
            override fun bindTheme(): Int {
                return R.style.CommonContentDialogStyle
            }

            override fun bindLayout(): Int {
                return R.layout.screen_dialog
            }

            override fun initView(dialog: BaseDialogFragment, contentView: View) {
                contentView.findViewById<ImageView>(R.id.screenDialogScreenshotIv)
                        .setImageBitmap(screenshot)
            }

            override fun setWindowStyle(window: Window) {}

            override fun onCancel(dialog: BaseDialogFragment) {}

            override fun onDismiss(dialog: BaseDialogFragment) {}
        }).show()
    }

    fun showToastDialog() {
        val topActivity = ActivityUtils.getTopActivity() ?: return
        BaseDialogFragment().init(topActivity as FragmentActivity, object : DialogLayoutCallback {
            override fun bindTheme(): Int {
                return R.style.CommonContentDialogStyle
            }

            override fun bindLayout(): Int {
                return R.layout.toast_dialog
            }

            override fun initView(dialog: BaseDialogFragment, contentView: View) {
                contentView.findViewById<Button>(R.id.toastDialogShowShortToastBtn)
                        .setOnClickListener { ToastUtils.showShort("Short") }
            }

            override fun setWindowStyle(window: Window) {}

            override fun onCancel(dialog: BaseDialogFragment) {}

            override fun onDismiss(dialog: BaseDialogFragment) {}
        }).show()
    }
}
