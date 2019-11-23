package com.blankj.utilcode.pkg.feature.activity

import android.content.Context
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.widget.ImageView
import com.blankj.base.rv.ItemViewHolder
import com.blankj.common.activity.CommonActivity
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemClick
import com.blankj.common.item.CommonItemImage
import com.blankj.common.item.CommonItemTitle
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.pkg.feature.CoreUtilActivity
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.CollectionUtils
import com.blankj.utilcode.util.StringUtils
import java.util.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/10/13
 * desc  : demo about ActivityUtils
 * ```
 */
class ActivityActivity : CommonActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, ActivityActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun bindTitleRes(): Int {
        return R.string.demo_activity
    }

    override fun bindItems(): List<CommonItem<*>> {
        val elementItem = ActivityItem()
        val intent = Intent(this, SubActivityActivity::class.java)
        val intents = arrayOfNulls<Intent>(2)
        intents[0] = intent
        intents[1] = Intent(this, SubActivityActivity::class.java)

        return CollectionUtils.newArrayList(
                elementItem,
                CommonItemTitle("isActivityExists(${SubActivityActivity::class.java.name})", ActivityUtils.isActivityExists(AppUtils.getAppPackageName(), SubActivityActivity::class.java.name).toString()),
                CommonItemTitle("getLauncherActivity", ActivityUtils.getLauncherActivity(AppUtils.getAppPackageName())),
                CommonItemTitle("getMainActivities", ActivityUtils.getMainActivities().toString()),
                CommonItemTitle("getTopActivity", ActivityUtils.getTopActivity().toString()),
                CommonItemTitle("isActivityExistsInStack", ActivityUtils.isActivityExistsInStack(CoreUtilActivity::class.java).toString()),
                CommonItemImage("getActivityIcon") {
                    it.setImageDrawable(ActivityUtils.getActivityIcon(ActivityActivity::class.java))
                },
                CommonItemImage("getActivityLogo") {
                    it.setImageDrawable(ActivityUtils.getActivityLogo(ActivityActivity::class.java))
                },

                CommonItemClick(R.string.activity_clz, true) {
                    ActivityUtils.startActivity(SubActivityActivity::class.java)
                },
                CommonItemClick(R.string.activity_clz_opt, true) {
                    ActivityUtils.startActivity(SubActivityActivity::class.java, getOption(elementItem))
                },
                CommonItemClick(R.string.activity_clz_anim, true) {
                    ActivityUtils.startActivity(SubActivityActivity::class.java, R.anim.fade_in_1000, R.anim.fade_out_1000)
                },
                CommonItemClick(R.string.activity_act_clz, true) {
                    ActivityUtils.startActivity(this, SubActivityActivity::class.java)
                },
                CommonItemClick(R.string.activity_act_clz_shared_element, true) {
                    ActivityUtils.startActivity(this, SubActivityActivity::class.java, elementItem.element)
                },
                CommonItemClick(R.string.activity_act_clz_anim, true) {
                    ActivityUtils.startActivity(this, SubActivityActivity::class.java, R.anim.fade_in_1000, R.anim.fade_out_1000)
                },
                CommonItemClick(R.string.activity_pkg_cls, true) {
                    ActivityUtils.startActivity(this.packageName, SubActivityActivity::class.java.name)
                },
                CommonItemClick(R.string.activity_pkg_cls_opt, true) {
                    ActivityUtils.startActivity(this.packageName, SubActivityActivity::class.java.name, getOption(elementItem))
                },
                CommonItemClick(R.string.activity_pkg_cls_anim, true) {
                    ActivityUtils.startActivity(this.packageName, SubActivityActivity::class.java.name, R.anim.fade_in_1000, R.anim.fade_out_1000)
                },
                CommonItemClick(R.string.activity_act_pkg_cls, true) {
                    ActivityUtils.startActivity(this, this.packageName, SubActivityActivity::class.java.name)
                },
                CommonItemClick(R.string.activity_act_pkg_cls_opt, true) {
                    ActivityUtils.startActivity(this, this.packageName, SubActivityActivity::class.java.name, getOption(elementItem))
                },
                CommonItemClick(R.string.activity_act_pkg_cls_shared_element, true) {
                    ActivityUtils.startActivity(this, this.packageName, SubActivityActivity::class.java.name, elementItem.element)
                },
                CommonItemClick(R.string.activity_act_pkg_cls_anim, true) {
                    ActivityUtils.startActivity(this, this.packageName, SubActivityActivity::class.java.name, R.anim.fade_in_1000, R.anim.fade_out_1000)
                },
                CommonItemClick(R.string.activity_intent, true) {
                    ActivityUtils.startActivity(this, intent)
                },
                CommonItemClick(R.string.activity_intent_opt, true) {
                    ActivityUtils.startActivity(this, intent, getOption(elementItem))
                },
                CommonItemClick(R.string.activity_intent_shared_element, true) {
                    ActivityUtils.startActivity(this, intent, elementItem.element)
                },
                CommonItemClick(R.string.activity_intent_anim, true) {
                    ActivityUtils.startActivity(this, intent, R.anim.fade_in_1000, R.anim.fade_out_1000)
                },
                CommonItemClick(R.string.activity_intents, true) {
                    ActivityUtils.startActivities(intents)
                },
                CommonItemClick(R.string.activity_intents_opt, true) {
                    ActivityUtils.startActivities(intents, getOption(elementItem))
                },
                CommonItemClick(R.string.activity_intents_anim, true) {
                    ActivityUtils.startActivities(intents, R.anim.fade_in_1000, R.anim.fade_out_1000)
                },
                CommonItemClick(R.string.activity_act_intents, true) {
                    ActivityUtils.startActivities(this, intents, R.anim.fade_in_1000, R.anim.fade_out_1000)
                },
                CommonItemClick(R.string.activity_act_intents_opt, true) {
                    ActivityUtils.startActivities(this, intents, getOption(elementItem))
                },
                CommonItemClick(R.string.activity_act_intents_anim, true) {
                    ActivityUtils.startActivities(this, intents, R.anim.fade_in_1000, R.anim.fade_out_1000)
                },
                CommonItemClick(R.string.activity_start_home_activity, true) {
                    ActivityUtils.startHomeActivity()
                },
                CommonItemClick(R.string.activity_start_launcher_activity, true) {
                    ActivityUtils.startLauncherActivity()
                },
                CommonItemClick(R.string.activity_finish_activity, false) {
                    ActivityUtils.finishActivity(CoreUtilActivity::class.java)
                },
                CommonItemClick(R.string.activity_finish_to_activity, true) {
                    ActivityUtils.finishToActivity(CoreUtilActivity::class.java, false, true)
                },
                CommonItemClick(R.string.activity_finish_all_activities, true) {
                    ActivityUtils.finishAllActivities()
                }
        )
    }

    private fun getOption(activityItem: ActivityItem): Bundle? {
        when (Random().nextInt(5)) {
            0 -> return ActivityOptionsCompat.makeCustomAnimation(this,
                    R.anim.slide_right_in_1000,
                    R.anim.slide_left_out_1000)
                    .toBundle()
            1 -> return ActivityOptionsCompat.makeScaleUpAnimation(activityItem.element,
                    activityItem.element.width / 2,
                    activityItem.element.height / 2,
                    0, 0)
                    .toBundle()
            2 -> return ActivityOptionsCompat.makeThumbnailScaleUpAnimation(activityItem.element,
                    (activityItem.element.drawable as BitmapDrawable).bitmap,
                    0, 0)
                    .toBundle()
            3 -> return ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                    activityItem.element,
                    StringUtils.getString(R.string.activity_shared_element))
                    .toBundle()
            else -> return ActivityOptionsCompat.makeClipRevealAnimation(activityItem.element,
                    activityItem.element.width / 2,
                    activityItem.element.height / 2,
                    0, 0)
                    .toBundle()
        }
    }
}

class ActivityItem : CommonItem<ActivityItem> {

    lateinit var element: ImageView;

    constructor() : super(R.layout.activity_item_shared_element_activity)

    override fun bind(holder: ItemViewHolder, position: Int) {
        super.bind(holder, position)
        element = holder.findViewById(R.id.activityViewSharedElement)
    }
}