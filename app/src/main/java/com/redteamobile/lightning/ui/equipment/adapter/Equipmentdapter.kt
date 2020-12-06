package com.redteamobile.lightning.ui.profile.adapter

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.redteamobile.lightning.App
import com.redteamobile.lightning.Global
import com.redteamobile.lightning.R
import com.redteamobile.lightning.data.local.sim.EuiccController
import com.redteamobile.lightning.data.local.sim.model.ProfileInfos
import com.redteamobile.lightning.data.remote.model.bean.EquipmentModel
import com.redteamobile.lightning.util.LogUtil
import com.redteamobile.lightning.util.UIUtil
import com.redteamobile.lightning.util.Util
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.item_equipment.view.*
import kotlinx.android.synthetic.main.item_esim_card.view.*
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

/**
 * @author SScience
 * @description
 */
class Equipmentdapter(val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TAG = "ESimCardAdapter"
    }

    private var dataList: List<EquipmentModel> = ArrayList()

    fun setData(dataList: List<EquipmentModel>) {
        this.dataList = dataList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TransRegionalHolder) {
            val any = dataList[position]
            holder.bind(any)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TransRegionalHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_equipment, parent, false)
        )
    }

    private class TransRegionalHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(equipment: EquipmentModel) {
            with(itemView) {
                tv_equipment_name.text = equipment.name
                tv_location.text = equipment.location
                tv_latitude.text = equipment.latitude
                tv_longitude.text = equipment.longitude
                tv_createDate.text = equipment.createDate
            }
        }

    }
}