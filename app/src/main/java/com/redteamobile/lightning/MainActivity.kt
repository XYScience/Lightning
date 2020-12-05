package com.redteamobile.lightning

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.redteamobile.lightning.data.remote.HttpManager
import com.redteamobile.lightning.ui.base.BaseActivity
import com.redteamobile.lightning.util.UIUtil
import com.redteamobile.lightning.util.toJson
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    companion object {
        private const val FRAGMENTS_TAG = "android:support:fragments"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        //删除保存的Fragment，也可以重写onSaveInstanceState方法不让其保存
        savedInstanceState?.putParcelable(FRAGMENTS_TAG, null)
        super.onCreate(savedInstanceState)
    }

    override fun getContentView(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        setSupportActionBar(toolbar)
        UIUtil.setStatusBarBackground(this, android.R.color.white)
        supportActionBar?.setBackgroundDrawable(resources.getDrawable(R.drawable.action_bar_background))
        val navView: BottomNavigationView = findViewById(R.id.navigation)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun initData() {
        HttpManager.getInstance(this).httpService.banner()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.e(">>>>>", "banner: ${it.toJson()}")
            }
    }
}