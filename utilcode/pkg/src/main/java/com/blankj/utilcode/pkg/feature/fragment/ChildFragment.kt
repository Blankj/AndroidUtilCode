package com.blankj.utilcode.pkg.feature.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.blankj.lib.base.BaseFragment
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.FragmentUtils
import com.blankj.utilcode.util.LogUtils
import java.util.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 17/02/02
 * desc  : demo about FragmentUtils
 * ```
 */
class ChildFragment : BaseFragment(), FragmentUtils.OnBackClickListener {

    private var tvAboutFragment: TextView? = null

    override fun initData(bundle: Bundle?) {

    }

    override fun bindLayout(): Int {
        return R.layout.fragment_child
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        val random = Random()
        FragmentUtils.setBackgroundColor(this, Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
        findViewById<View>(R.id.fragmentRootShowAboutBtn).setOnClickListener(this)
        findViewById<View>(R.id.btn_pop).setOnClickListener(this)
        tvAboutFragment = findViewById(R.id.fragmentRootAboutTv)
    }

    override fun doBusiness() {

    }

    override fun onWidgetClick(view: View) {
        tvAboutFragment!!.text = ""
        val i = view.id
        if (i == R.id.fragmentRootShowAboutBtn) {
            tvAboutFragment!!.text = ("top: " + FragmentUtils.getSimpleName(FragmentUtils.getTop(fragmentManager!!))
                    + "\ntopInStack: " + FragmentUtils.getSimpleName(FragmentUtils.getTopInStack(fragmentManager!!))
                    + "\ntopShow: " + FragmentUtils.getSimpleName(FragmentUtils.getTopShow(fragmentManager!!))
                    + "\ntopShowInStack: " + FragmentUtils.getSimpleName(FragmentUtils.getTopShowInStack(fragmentManager!!))
                    + "\n---all of fragments---\n"
                    + FragmentUtils.getAllFragments(fragmentManager!!).toString()
                    + "\n----------------------\n\n"
                    + "---stack top---\n"
                    + FragmentUtils.getAllFragmentsInStack(fragmentManager!!).toString()
                    + "\n---stack bottom---\n\n")

            //            case R.id.btn_pop:
            //                FragmentUtils.popFragment(getFragmentManager());
            //                break;
        }
    }

    override fun onBackClick(): Boolean {
        LogUtils.d("demo2 onBackClick")
        return false
    }

    companion object {

        fun newInstance(): ChildFragment {
            val args = Bundle()
            val fragment = ChildFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
