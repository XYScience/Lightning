package com.redteamobile.lightning

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.redteamobile.lightning.data.remote.HttpManager
import com.redteamobile.lightning.ui.base.BaseActivity
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
        fab.setOnClickListener {
            Toast.makeText(this, "666", Toast.LENGTH_SHORT).show()
        }
    }

    override fun initData() {
        HttpManager.getInstance(this).httpService.banner()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.e(">>>>>", "banner: ${it.toJson()}")
            }
    }
}