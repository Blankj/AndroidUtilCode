package com.blankj.utilcode.pkg.feature.activity

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.view.View
import android.view.Window
import com.blankj.lib.base.BaseTitleBarActivity
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.pkg.feature.CoreUtilActivity
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SpanUtils
import kotlinx.android.synthetic.main.activity_activity.*
import java.util.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/10/13
 * desc  : demo about ActivityUtils
 * ```
 */
class ActivityActivity : BaseTitleBarActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, ActivityActivity::class.java)
            context.startActivity(starter)
        }
    }

    internal var random = Random()
    lateinit var bitmap: Bitmap
    internal lateinit var intent: Intent
    private val intents = arrayOfNulls<Intent>(2)

    override fun bindTitle(): String {
        return getString(R.string.demo_activity)
    }

    override fun initData(bundle: Bundle?) {}

    override fun bindLayout(): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
        }
        return R.layout.activity_activity
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        activityClzBtn.setOnClickListener(this)
        activityClzOptBtn.setOnClickListener(this)
        activityClzAnimBtn.setOnClickListener(this)
        activityActClzBtn.setOnClickListener(this)
        activityActClzOptBtn.setOnClickListener(this)
        activityActClzSharedElementBtn.setOnClickListener(this)
        activityActClzAnimBtn.setOnClickListener(this)
        activityPkgClsBtn.setOnClickListener(this)
        activityPkgClsOptBtn.setOnClickListener(this)
        activityPkgClsAnimBtn.setOnClickListener(this)
        activityActPkgClsBtn.setOnClickListener(this)
        activityActPkgClsOptBtn.setOnClickListener(this)
        activityActPkgClsSharedElementBtn.setOnClickListener(this)
        activityActPkgClsAnimBtn.setOnClickListener(this)
        activityIntentBtn.setOnClickListener(this)
        activityIntentOptBtn.setOnClickListener(this)
        activityIntentSharedElementBtn.setOnClickListener(this)
        activityIntentAnimBtn.setOnClickListener(this)
        activityIntentsBtn.setOnClickListener(this)
        activityIntentsOptBtn.setOnClickListener(this)
        activityIntentsAnimBtn.setOnClickListener(this)
        activityActIntentsBtn.setOnClickListener(this)
        activityActIntentsOptBtn.setOnClickListener(this)
        activityActIntentsAnimBtn.setOnClickListener(this)
        activityActClzSharedElementBtn.setOnClickListener(this)
        activityStartHomeActivityBtn.setOnClickListener(this)
        activityFinishActivityBtn.setOnClickListener(this)
        activityFinishToActivityBtn.setOnClickListener(this)
        activityFinishAllActivitiesBtn.setOnClickListener(this)
        SpanUtils.with(activityAboutTv)
                .appendLine("isActivityExists: " + ActivityUtils.isActivityExists(AppUtils.getAppPackageName(), SubActivityActivity::class.java.name))
                .appendLine("getLauncherActivity: " + ActivityUtils.getLauncherActivity(AppUtils.getAppPackageName()))
                .appendLine("getTopActivity: " + ActivityUtils.getTopActivity())
                .appendLine("isActivityExistsInStack: " + ActivityUtils.isActivityExistsInStack(CoreUtilActivity::class.java))
                .append("getActivityIcon: ")
                .appendImage(ActivityUtils.getActivityIcon(this), SpanUtils.ALIGN_CENTER)
                .appendLine()
                .append("getActivityLogo: ")
                .appendImage(ActivityUtils.getActivityLogo(this), SpanUtils.ALIGN_CENTER)
                .create()
        bitmap = (activityViewSharedElement.drawable as BitmapDrawable).bitmap

        intent = Intent(this, SubActivityActivity::class.java)
        intents[0] = intent
        intents[1] = Intent(this, SubActivityActivity::class.java)
    }

    override fun doBusiness() {}

    override fun onWidgetClick(view: View) {
        when (view.id) {
            R.id.activityClzBtn -> ActivityUtils.startActivity(SubActivityActivity::class.java)
            R.id.activityClzOptBtn -> ActivityUtils.startActivity(SubActivityActivity::class.java,
                    getOption(random.nextInt(5)))
            R.id.activityClzAnimBtn -> ActivityUtils.startActivity(SubActivityActivity::class.java,
                    R.anim.fade_in_1000, R.anim.fade_out_1000)
            R.id.activityActClzBtn -> ActivityUtils.startActivity(this,
                    SubActivityActivity::class.java)
            R.id.activityActClzOptBtn -> ActivityUtils.startActivity(this,
                    SubActivityActivity::class.java,
                    getOption(random.nextInt(5)))
            R.id.activityActClzSharedElementBtn -> ActivityUtils.startActivity(this,
                    SubActivityActivity::class.java,
                    activityViewSharedElement)
            R.id.activityActClzAnimBtn -> ActivityUtils.startActivity(this,
                    SubActivityActivity::class.java,
                    R.anim.fade_in_1000, R.anim.fade_out_1000)
            R.id.activityPkgClsBtn -> ActivityUtils.startActivity(this.packageName,
                    SubActivityActivity::class.java.name)
            R.id.activityPkgClsOptBtn -> ActivityUtils.startActivity(this.packageName,
                    SubActivityActivity::class.java.name,
                    getOption(random.nextInt(5)))
            R.id.activityPkgClsAnimBtn -> ActivityUtils.startActivity(this.packageName,
                    SubActivityActivity::class.java.name,
                    R.anim.fade_in_1000, R.anim.fade_out_1000)
            R.id.activityActPkgClsBtn -> ActivityUtils.startActivity(this,
                    this.packageName,
                    SubActivityActivity::class.java.name)
            R.id.activityActPkgClsOptBtn -> ActivityUtils.startActivity(this,
                    this.packageName,
                    SubActivityActivity::class.java.name,
                    getOption(random.nextInt(5)))
            R.id.activityActPkgClsSharedElementBtn -> ActivityUtils.startActivity(this,
                    this.packageName,
                    SubActivityActivity::class.java.name,
                    activityViewSharedElement)
            R.id.activityActPkgClsAnimBtn -> ActivityUtils.startActivity(this,
                    this.packageName,
                    SubActivityActivity::class.java.name,
                    R.anim.fade_in_1000, R.anim.fade_out_1000)
            R.id.activityIntentBtn -> ActivityUtils.startActivity(this, intent)
            R.id.activityIntentOptBtn -> ActivityUtils.startActivity(this, intent, getOption(random.nextInt(5)))
            R.id.activityIntentSharedElementBtn -> ActivityUtils.startActivity(this, intent, activityViewSharedElement)
            R.id.activityIntentAnimBtn -> ActivityUtils.startActivity(this, intent,
                    R.anim.fade_in_1000, R.anim.fade_out_1000)
            R.id.activityIntentsBtn -> ActivityUtils.startActivities(intents)
            R.id.activityIntentsOptBtn -> ActivityUtils.startActivities(intents, getOption(random.nextInt(5)))
            R.id.activityIntentsAnimBtn -> ActivityUtils.startActivities(intents,
                    R.anim.fade_in_1000, R.anim.fade_out_1000)
            R.id.activityActIntentsBtn -> ActivityUtils.startActivities(this, intents,
                    R.anim.fade_in_1000, R.anim.fade_out_1000)
            R.id.activityActIntentsOptBtn -> ActivityUtils.startActivities(this, intents, getOption(random.nextInt(5)))
            R.id.activityActIntentsAnimBtn -> ActivityUtils.startActivities(this, intents,
                    R.anim.fade_in_1000, R.anim.fade_out_1000)
            R.id.activityStartHomeActivityBtn -> ActivityUtils.startHomeActivity()
            R.id.activityFinishActivityBtn -> ActivityUtils.finishActivity(CoreUtilActivity::class.java)
            R.id.activityFinishToActivityBtn -> ActivityUtils.finishToActivity(CoreUtilActivity::class.java, false, true)
            R.id.activityFinishAllActivitiesBtn -> ActivityUtils.finishAllActivities()
        }
    }

    private fun getOption(type: Int): Bundle? {
        LogUtils.d(type)
        when (type) {
            0 -> return ActivityOptionsCompat.makeCustomAnimation(this,
                    R.anim.slide_in_right_1000,
                    R.anim.slide_out_left_1000)
                    .toBundle()
            1 -> return ActivityOptionsCompat.makeScaleUpAnimation(activityViewSharedElement,
                    activityViewSharedElement.width / 2,
                    activityViewSharedElement.height / 2,
                    0, 0)
                    .toBundle()
            2 -> return ActivityOptionsCompat.makeThumbnailScaleUpAnimation(activityViewSharedElement,
                    bitmap,
                    0, 0)
                    .toBundle()
            3 -> return ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                    activityViewSharedElement,
                    getString(R.string.activity_shared_element))
                    .toBundle()
            4 -> return ActivityOptionsCompat.makeClipRevealAnimation(activityViewSharedElement,
                    activityViewSharedElement.width / 2,
                    activityViewSharedElement.height / 2,
                    0, 0)
                    .toBundle()
            else -> return null
        }
    }
}
