package com.redteamobile.lightning.ui.profile.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.redteamobile.lightning.R
import com.redteamobile.lightning.data.local.cache.LogicCache
import com.redteamobile.lightning.data.remote.HttpManager
import com.redteamobile.lightning.data.remote.model.bean.TaskModel
import com.redteamobile.lightning.data.remote.model.bean.UserModel
import com.redteamobile.lightning.data.remote.model.request.TaskGetRequest
import com.redteamobile.lightning.util.Util
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.item_task.view.*

/**
 * @author SScience
 * @description
 */
class TaskAdapter(val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var userModel: UserModel? = null

    init {
        userModel = LogicCache.userInfo(context)
    }

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
                .inflate(R.layout.item_task, parent, false),
            userModel?.id
        )
    }

    private class TransRegionalHolder(itemView: View, var userId: Int?) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(task: TaskModel) {
            with(itemView) {
                var statusStr: String? = null
                var statusBg: Drawable? = null
                var statusText: Int = -1
                var statusEnable: Boolean = false
                when (task.status) {
                    0 -> {
                        statusEnable = false
                        statusStr = "Finished"
                        statusBg = resources.getDrawable(R.drawable.shape_status_grey_bg)
                        statusText = resources.getColor(R.color.item_background_grey_text)
                    }
                    1 -> {
                        statusEnable = true
                        statusStr = "Testable"
                        statusBg = resources.getDrawable(R.drawable.shape_status_green_bg)
                        statusText = resources.getColor(R.color.item_background_green_text)
                    }
                    2 -> {
                        statusEnable = false
                        statusStr = "Canceled"
                        statusBg = resources.getDrawable(R.drawable.shape_status_grey_bg)
                        statusText = resources.getColor(R.color.item_background_grey_text)
                    }
                }

                tv_task_name.text = task.name
                tv_status.isEnabled = statusEnable
                tv_status.text = statusStr
                tv_status.background = statusBg
                tv_status.setTextColor(statusText)
                tv_time.text = task.createDate
                tv_coins.text = "Reward: ${task.coins} $"
                if (task.result.isNullOrEmpty()) {
                    tv_result.visibility = View.GONE
                } else {
                    tv_result.visibility = View.VISIBLE
                    tv_result.text = task.result
                }

                tv_status.setOnClickListener {
                    if (userId != null) {
                        HttpManager.getInstance(context).httpService.startTask(
                            TaskGetRequest(
                                userId!!,
                                task.id!!,
                                "666"
                            )
                        )
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe {
                            }
                    }
                }
            }
        }
    }
}