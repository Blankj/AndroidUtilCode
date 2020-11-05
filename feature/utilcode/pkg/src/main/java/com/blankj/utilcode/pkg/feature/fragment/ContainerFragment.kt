package com.blankj.utilcode.pkg.feature.fragment

import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.transition.*
import android.view.View
import android.widget.ImageView
import com.blankj.base.rv.ItemViewHolder
import com.blankj.common.fragment.CommonFragment
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemClick
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.pkg.helper.DialogHelper
import com.blankj.utilcode.util.*
import kotlinx.android.synthetic.main.fragment_container.*
import java.util.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 17/02/02
 * desc  : demo about FragmentUtils
 * ```
 */
class ContainerFragment : CommonFragment(), FragmentUtils.OnBackClickListener {

    companion object {
        fun newInstance(): ContainerFragment {
            val args = Bundle()
            val fragment = ContainerFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var fm: FragmentManager
    private val mBgColor = ColorUtils.getRandomColor(false)

    override fun bindLayout(): Int {
        return R.layout.fragment_container
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View?) {
        super.initView(savedInstanceState, contentView)
        mContentView.setBackgroundColor(mBgColor)
        fm = fragmentManager!!
        setCommonItems(findViewById(R.id.commonItemRv), getItems())
    }

    private fun getItems(): ArrayList<CommonItem<*>>? {
        val item = SharedElementItem()
        return CollectionUtils.newArrayList<CommonItem<*>>(
                CommonItemClick(R.string.fragment_show_stack) {
                    DialogHelper.showFragmentDialog(
                            SpanUtils().appendLine("top: " + FragmentUtils.getSimpleName(FragmentUtils.getTop(fm)))
                                    .appendLine("topInStack: " + FragmentUtils.getSimpleName(FragmentUtils.getTopInStack(fm)))
                                    .appendLine("topShow: " + FragmentUtils.getSimpleName(FragmentUtils.getTopShow(fm)))
                                    .appendLine("topShowInStack: " + FragmentUtils.getSimpleName(FragmentUtils.getTopShowInStack(fm)))
                                    .appendLine()
                                    .appendLine("---all of fragments---")
                                    .appendLine(FragmentUtils.getAllFragments(fm).toString())
                                    .appendLine("----------------------")
                                    .appendLine()
                                    .appendLine("---stack top---")
                                    .appendLine(FragmentUtils.getAllFragmentsInStack(fm).toString())
                                    .appendLine("---stack bottom---")
                                    .create()
                    )
                },
                CommonItemClick(R.string.fragment_add_child) {
                    FragmentUtils.add(
                            fm,
                            ChildFragment.newInstance(),
                            id
                    )
                },
                CommonItemClick(R.string.fragment_add_child_stack) {
                    FragmentUtils.add(
                            fm,
                            ChildFragment.newInstance(),
                            id,
                            false,
                            true
                    )
                },
                CommonItemClick(R.string.fragment_add_hide) {
                    FragmentUtils.add(
                            fm,
                            ChildFragment.newInstance(),
                            id,
                            true
                    )
                },
                CommonItemClick(R.string.fragment_add_hide_stack) {
                    FragmentUtils.add(
                            fm,
                            ChildFragment.newInstance(),
                            id,
                            true,
                            true
                    )
                },
                CommonItemClick(R.string.fragment_add_demo1_show) {
                    FragmentUtils.add(
                            fm,
                            addSharedElement(ChildFragment.newInstance()),
                            id,
                            false,
                            false
                    )
                },
                CommonItemClick(R.string.fragment_pop_to_root) {
                    FragmentUtils.popTo(
                            fm,
                            ChildFragment::class.java,
                            true
                    )
                },
                CommonItemClick(R.string.fragment_hide_demo0_show_demo1) {
                    val fragment1 = FragmentUtils.findFragment(fm, ChildFragment::class.java)
                    if (fragment1 != null) {
                        FragmentUtils.showHide(this, fragment1)
                    } else {
                        ToastUtils.showLong("please add demo1 first!")
                    }
                },
                CommonItemClick(R.string.fragment_replace) {
                    FragmentUtils.replace(
                            fm,
                            addSharedElement(ChildFragment.newInstance()),
                            id,
                            true,
                            item.element
                    )
                },
                item
        ).apply {
            for (ci: CommonItem<*> in this) {
                ci.backgroundColor = mBgColor
            }
        }
    }

    private fun addSharedElement(fragment: Fragment): Fragment {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fragment.sharedElementEnterTransition = DetailTransition()
            fragment.enterTransition = Fade()
            fragment.sharedElementReturnTransition = DetailTransition()
        }
        return fragment
    }

    override fun onBackClick(): Boolean {
        return false
    }
}

class SharedElementItem : CommonItem<SharedElementItem> {

    lateinit var element: ImageView;

    constructor() : super(R.layout.fragment_item_shared_element)

    override fun bind(holder: ItemViewHolder, position: Int) {
        super.bind(holder, position)
        element = holder.findViewById(R.id.fragmentRootSharedElementIv)
    }
}

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class DetailTransition() : TransitionSet() {
    init {
        ordering = ORDERING_TOGETHER
        addTransition(ChangeBounds()).addTransition(ChangeTransform()).addTransition(ChangeImageTransform())
    }
}
