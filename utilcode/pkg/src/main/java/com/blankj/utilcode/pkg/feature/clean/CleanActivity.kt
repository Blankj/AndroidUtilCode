package com.blankj.utilcode.pkg.feature.clean

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.view.View
import com.blankj.lib.base.BaseTitleBarActivity
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.CleanUtils
import com.blankj.utilcode.util.SnackbarUtils
import kotlinx.android.synthetic.main.activity_clean.*
import java.io.File

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/09/29
 * desc  : demo about CleanUtils
 * ```
 */
class CleanActivity : BaseTitleBarActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, CleanActivity::class.java)
            context.startActivity(starter)
        }
    }

    private lateinit var snackBarRootView: View
    private lateinit var internalCachePath: String
    private lateinit var internalFilesPath: String
    private lateinit var internalDbs: String
    private lateinit var internalSp: String
    private lateinit var externalCache: String

    override fun bindTitle(): CharSequence {
        return getString(R.string.demo_clean)
    }

    override fun initData(bundle: Bundle?) {}

    override fun bindLayout(): Int {
        return R.layout.activity_clean
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        snackBarRootView = findViewById(android.R.id.content)
        cleanInternalCacheBtn.setOnClickListener(this)
        cleanInternalFilesBtn.setOnClickListener(this)
        cleanInternalDatabasesBtn.setOnClickListener(this)
        cleanInternalSpBtn.setOnClickListener(this)
        cleanExternalCacheBtn.setOnClickListener(this)

        internalCachePath = cacheDir.path
        internalFilesPath = filesDir.path
        internalDbs = filesDir.parent + File.separator + "databases"
        internalSp = filesDir.parent + File.separator + "shared_prefs"

        if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
            externalCache = externalCacheDir.absolutePath
        }
    }

    override fun doBusiness() {}

    override fun onWidgetClick(view: View) {
        when (view.id) {
            R.id.cleanInternalCacheBtn -> showSnackbar(CleanUtils.cleanInternalCache(), internalCachePath)
            R.id.cleanInternalFilesBtn -> showSnackbar(CleanUtils.cleanInternalFiles(), internalFilesPath)
            R.id.cleanInternalDatabasesBtn -> showSnackbar(CleanUtils.cleanInternalDbs(), internalDbs)
            R.id.cleanInternalSpBtn -> showSnackbar(CleanUtils.cleanInternalSp(), internalSp)
            R.id.cleanExternalCacheBtn -> showSnackbar(CleanUtils.cleanExternalCache(), externalCache)
        }
    }

    private fun showSnackbar(isSuccess: Boolean, path: String?) {
        SnackbarUtils.with(snackBarRootView)
                .setDuration(SnackbarUtils.LENGTH_LONG)
                .apply {
                    if (isSuccess) {
                        setMessage("clean \"$path\" dir success")
                        showSuccess()
                    } else {
                        setMessage("clean \"$path\" dir failed")
                        showError()
                    }
                }
    }
}
