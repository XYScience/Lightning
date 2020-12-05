package com.redteamobile.lightning.util

import android.app.Activity
import io.reactivex.Observable

object Activities {

    private const val TAG = "Activities"

    var isInForeground = false
        set(foreground) {
            field = foreground
            LogUtil.d(TAG, "Set foreground: $foreground")
        }

    val activityList = ArrayList<Activity>()

    fun hasActivity(): Boolean {
        return activityList.isNotEmpty()
    }

    fun add(activity: Activity?) {
        if (activity != null) {
            activityList.add(activity)
        }
    }

    fun remove(activity: Activity?) {
        if (activity != null) {
            activityList.remove(activity)
        }
    }

    fun top(): Activity {
        return activityList.first()
    }

    fun isTop(activity: Activity): Boolean {
        return activityList.isNotEmpty() && (activity == top())
    }

    fun allActivity(): Observable<Activity> {
        return Observable.fromIterable(activityList)
    }

    fun finish(clazz: Class<out Activity>) {
        val temp = ArrayList<Activity>()
        allActivity()
            .filter {
                clazz.isAssignableFrom(it.javaClass)
            }
            .doOnNext {
                temp.add(it)
            }
            .doOnComplete {
                temp.forEach {
                    it.finish()
                }
            }
            .subscribe()
    }

}
