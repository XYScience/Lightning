package com.redteamobile.lightning.ui.profile.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.redteamobile.lightning.R
import com.redteamobile.lightning.data.remote.model.bean.TaskModel
import com.redteamobile.lightning.util.Util
import kotlinx.android.synthetic.main.item_task.view.*

/**
 * @author SScience
 * @description
 */
class TaskAdapter(val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var dataList: List<TaskModel> = ArrayList()

    fun setData(dataList: List<TaskModel>) {
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
                .inflate(R.layout.item_task, parent, false)
        )
    }

    private class TransRegionalHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(task: TaskModel) {
            with(itemView) {
                tv_task_name.text = task.name
                when (task.status) {
                    0 -> {
                        tv_status.isEnabled = true
                        tv_status.text = "Testable"
                        tv_status.background = resources.getDrawable(R.drawable.shape_status_green_bg)
                        tv_status.setTextColor(resources.getColor(R.color.item_background_green_text))
                    }
                    1 -> {
                        tv_status.isEnabled = false
                        tv_status.text = "Canceled"
                        tv_status.background = resources.getDrawable(R.drawable.shape_status_grey_bg)
                        tv_status.setTextColor(resources.getColor(R.color.item_background_grey_text))
                    }
                    2 -> {
                        tv_status.isEnabled = false
                        tv_status.text = "Tested"
                        tv_status.background = resources.getDrawable(R.drawable.shape_status_grey_bg)
                        tv_status.setTextColor(resources.getColor(R.color.item_background_grey_text))
                    }
                }
                tv_time.text = Util.formatTime(task.createDate)
                tv_coins.text = "Reward: ${task.coins} $"

                tv_status.setOnClickListener {
                    Toast.makeText(context, "开始测试..", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}