package com.redteamobile.lightning.util

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.redteamobile.lightning.R
import java.util.*

/**
 * @author SScience
 * @description
 */
object UIUtil {

    private const val TAG = "UIUtil"

    fun setStatusBarBackground(activity: Activity, colorRes: Int) {
        activity.window.statusBarColor = activity.getColor(colorRes)
    }

    fun setStatusBarTextColor(activity: Activity, isGray: Boolean) {
        val decorView = activity.window.decorView
        var vis = decorView.systemUiVisibility
        vis = if (UIUtil.isDarkTheme(activity)) {
            // 白色
            vis and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        } else {
            if (isGray) {
                // 灰色
                vis or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                // 白色
                vis and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            }
        }
        decorView.systemUiVisibility = vis
    }

    fun setNavigationBarIconColor(activity: Activity, isGray: Boolean) {
        if (isDarkTheme(activity)) {
            activity.window.navigationBarColor = activity.getColor(R.color.navigation_background)
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val decorView = activity.window.decorView
                var vis = decorView.systemUiVisibility
                vis = if (isGray) {
                    // 灰色
                    vis or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                } else {
                    // 白色
                    vis and View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR.inv()
                }
                decorView.systemUiVisibility = vis
            }
        }
    }

    fun setFullScreen(activity: Activity) {
        val decorView = activity.window.decorView
        val vis = decorView.systemUiVisibility
        decorView.systemUiVisibility = vis or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    }

    fun isDarkTheme(context: Context): Boolean {
        val flag = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return flag == Configuration.UI_MODE_NIGHT_YES
    }

    fun dp2px(context: Context, dpValue: Float): Int {
        val scale: Float = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    fun px2dp(context: Context, pxValue: Float): Int {
        val scale: Float = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    fun getLocaleDefault(): String {
        Log.i(TAG, "getLocaleDefault: " + Locale.getDefault().language)
        return when (Locale.getDefault().language) {
            "zh" -> "zh"
            else -> "en"
        }
    }

    fun showLoading(context: Context): AlertDialog {
        return showLoading(context, "Loading..")
    }

    fun showLoading(context: Context, msg: String?): AlertDialog {
        val loadingView = View.inflate(context, R.layout.view_loading, null)
        val dialogLoading = AlertDialog.Builder(context)
            .setView(loadingView)
            .setCancelable(false)
            .show()
        val tvLoading = loadingView.findViewById<TextView>(R.id.tvLoading)
        tvLoading?.visibility = if (msg.isNullOrBlank()) {
            View.GONE
        } else {
            tvLoading.text = msg
            View.VISIBLE
        }
        return dialogLoading
    }

    fun dismissLoading(dialogLoading: AlertDialog?) {
        dialogLoading?.dismiss()
    }
}