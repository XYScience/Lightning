package com.redteamobile.lightning

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.redteamobile.lightning.data.remote.HttpManager
import com.redteamobile.lightning.data.remote.model.request.TaskGetRequest
import com.redteamobile.lightning.ui.base.BaseActivity
import com.redteamobile.lightning.util.Activities
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
        supportActionBar?.setBackgroundDrawable(resources.getDrawable(R.drawable.action_bar_background))
        val navView: BottomNavigationView = findViewById(R.id.navigation)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_profile, R.id.navigation_task, R.id.navigation_me
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val elevation = UIUtil.dp2px(this@MainActivity, 4f).toFloat()
        navView.setOnNavigationItemSelectedListener(object :
            BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                navController.navigate(item.itemId)
                when (item.itemId) {
                    R.id.navigation_profile -> {
                        app_bar.elevation = elevation
                        return true
                    }
                    R.id.navigation_task -> {
                        app_bar.elevation = elevation
                        return true
                    }
                    R.id.navigation_me -> {
                        app_bar.elevation = 0f
                        return true
                    }
                }
                return false
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Activities.finish(MainActivity::class.java)
    }

    override fun initData() {
        HttpManager.getInstance(this).httpService.userInfo()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
            }
    }
}