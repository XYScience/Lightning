package com.redteamobile.lightning.ui.equipment

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.redteamobile.lightning.R
import com.redteamobile.lightning.data.local.cache.LogicCache
import com.redteamobile.lightning.ui.base.BaseActivity
import com.redteamobile.lightning.ui.profile.adapter.Equipmentdapter
import kotlinx.android.synthetic.main.activity_equipment.*

/**
 * @author SScience
 * @description
 */
class EquipmentsActivity : BaseActivity() {

    private var equipmentdapter: Equipmentdapter? = null

    override fun getContentView(): Int {
        return R.layout.activity_equipment
    }

    override fun initView() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        list_equipment.layoutManager = linearLayoutManager
        val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        list_equipment.addItemDecoration(dividerItemDecoration)
        equipmentdapter = Equipmentdapter(this)
        list_equipment.adapter = equipmentdapter
        val equipments = LogicCache.getEquipment(this)
        equipments?.let {
            equipmentdapter?.setData(equipments)
        }
    }

    override fun initData() {

    }
}