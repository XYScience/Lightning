package com.redteamobile.lightning.ui.me

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.redteamobile.lightning.R
import com.redteamobile.lightning.data.local.cache.LogicCache
import com.redteamobile.lightning.data.remote.HttpManager
import com.redteamobile.lightning.ui.equipment.EquipmentsActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_me.*

class MeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_me, container, false)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        updateData()
        initData()
    }

    private fun initData() {
        activity?.let { a ->
            HttpManager.getInstance(a).httpService.userInfo()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    updateData()
                }
        }
    }

    private fun updateData() {
        activity?.let { a ->
            var userModel = LogicCache.userInfo(a)
            tv_name.text = "${userModel?.name}/${userModel?.nickName}"
            tv_phone.text = userModel?.telephone
            tv_credit.text = "$ ${userModel?.coins}"

            rl_equipment.setOnClickListener {
                val intent = Intent(a, EquipmentsActivity::class.java)
                startActivity(intent)
            }
        }
    }
}