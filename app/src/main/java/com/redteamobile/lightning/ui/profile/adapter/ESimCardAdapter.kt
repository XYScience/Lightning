package com.redteamobile.lightning.ui.profile.adapter

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.redteamobile.lightning.R
import com.redteamobile.lightning.data.local.sim.model.ProfileInfos
import kotlinx.android.synthetic.main.item_esim_card.view.*

/**
 * @author SScience
 * @description
 */
class ESimCardAdapter(val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var dataList: List<ProfileInfos> = ArrayList()

    fun setData(dataList: List<ProfileInfos>) {
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
                .inflate(R.layout.item_esim_card, parent, false)
        )
    }

    private class TransRegionalHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(profileInfo: ProfileInfos) {
            with(itemView) {
                tv_status.text = if (profileInfo.profileState == 0) "UNUSED" else "USED"
                if (profileInfo.simSlotIndex == -1) {
                    tv_status.background = resources.getDrawable(R.drawable.shape_status_yellow_bg)
                    tv_status.setTextColor(resources.getColor(R.color.item_background_yellow_text))
                } else {
                    tv_status.background = resources.getDrawable(R.drawable.shape_status_green_bg)
                    tv_status.setTextColor(resources.getColor(R.color.item_background_green_text))
                }
                tv_carrier_name.text = profileInfo.carrierName
                tv_iccid.text = "ICCID: ${profileInfo.iccId}"
                tv_mcc.text = "MCC: ${profileInfo.mccString}"
                tv_mnc.text = "MNC: ${profileInfo.mncString}"
                tv_net_type.text = "Net: ${profileInfo.netType}"
                tv_phone_type.text = "Phone: ${profileInfo.phoneType}"
            }
        }
    }
}