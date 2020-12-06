package com.redteamobile.lightning

import android.annotation.SuppressLint
import android.content.Context
import com.redteamobile.lightning.data.local.sim.EuiccController
import com.redteamobile.lightning.util.LogUtil

class Global private constructor(context: Context) {

    companion object {
        private const val TAG = "SimManager"

        @SuppressLint("StaticFieldLeak")
        private var instance: Global? = null

        fun getInstance(context: Context): Global {
            if (instance == null) {
                synchronized(Global::class.java) {
                    if (instance == null) {
                        instance = Global(context)
                    }
                }
            }
            return instance!!
        }
    }

    lateinit var euiccController: EuiccController

    private val context = context.applicationContext

    fun init() {
        euiccController = EuiccController(context)
        LogUtil.initLogMode(context)
    }

}
