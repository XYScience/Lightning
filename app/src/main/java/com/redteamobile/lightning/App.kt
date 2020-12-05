package com.redteamobile.lightning

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.TextUtils
import com.redteamobile.lightning.util.Activities
import io.reactivex.plugins.RxJavaPlugins

class App : Application(), Application.ActivityLifecycleCallbacks {

    companion object {

        const val TAG = "RedteaApp"

        @SuppressLint("StaticFieldLeak")
        lateinit var instance: Context

        private var foregroundCount = 0
        const val KEY_PLAN_DATA_SHOW = "eSIM_NumberNotShow_Remote"
    }

    override fun onCreate() {
        super.onCreate()

        instance = applicationContext
        registerActivityLifecycleCallbacks(this)

        initModule()

        RxJavaPlugins.setErrorHandler {
            it?.run {
                this.message
            }
        }

    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStarted(activity: Activity) {
        if (foregroundCount == 0) {
            toggle(true)
        }
        foregroundCount++
    }

    override fun onActivityDestroyed(activity: Activity) {
        Activities.remove(activity)
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityStopped(activity: Activity) {
        foregroundCount--
        if (foregroundCount == 0) {
            toggle(false)
        }
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        Activities.add(activity)
    }

    override fun onActivityResumed(activity: Activity) {
    }


    private fun toggle(foreground: Boolean) {
        Activities.isInForeground = foreground
    }

    private fun initModule() {
        Global.getInstance(this).init()
    }

    fun exitApp() {
        Activities.allActivity().subscribe {
            it.finish()
        }.dispose()
    }


}
