package com.redteamobile.lightning

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomappbar.BottomAppBar

class MainActivity : AppCompatActivity() {

    companion object {
        private const val FRAGMENTS_TAG = "android:support:fragments"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        //删除保存的Fragment，也可以重写onSaveInstanceState方法不让其保存
        savedInstanceState?.putParcelable(FRAGMENTS_TAG, null)

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val navView: BottomAppBar = findViewById(R.id.bottom_app_bar)


    }
}