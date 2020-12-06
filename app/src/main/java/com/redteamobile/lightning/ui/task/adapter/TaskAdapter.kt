package com.redteamobile.lightning.ui.profile.adapter

import android.app.Activity
import android.app.Dialog
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.redteamobile.lightning.R
import com.redteamobile.lightning.data.local.cache.LogicCache
import com.redteamobile.lightning.data.remote.HttpManager
import com.redteamobile.lightning.data.remote.model.bean.TaskModel
import com.redteamobile.lightning.data.remote.model.bean.UserModel
import com.redteamobile.lightning.data.remote.model.request.TaskGetRequest
import com.redteamobile.lightning.data.remote.util.DownloadTest
import com.redteamobile.lightning.data.remote.util.SpeedTestException
import com.redteamobile.lightning.data.remote.util.UploadTest
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.item_task.view.*

/**
 * @author SScience
 * @description
 */
class TaskAdapter(val activity: Activity) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var userModel: UserModel? = null

    init {
        userModel = LogicCache.userInfo(activity)
    }

    private var dataList: ArrayList<TaskModel> = ArrayList()

    fun setData(dataList: ArrayList<TaskModel>) {
        this.dataList.clear()
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
            activity,
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_task, parent, false),
            userModel?.id
        )
    }

    private class TransRegionalHolder(
        var activity: Activity,
        itemView: View,
        var userId: Int?
    ) :
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
                        startTest(activity, task.id!!)
                    }
                }
            }
        }

        private fun startTest(activity: Activity, taskId: Int) {
            val view = LayoutInflater.from(activity).inflate(R.layout.dialog_test, null)

            var dialog = AlertDialog.Builder(activity)
                .setTitle("Tips")
                .setView(view)
                .setCancelable(false)
                .create()
            dialog.show()

            download(activity, dialog, view, taskId)
        }

        private fun download(
            activity: Activity,
            dialog: Dialog,
            view: View,
            taskId: Int
        ) {
            val downloadTextView = view.findViewById<TextView>(R.id.downloadTextView)

            DownloadTest("", object : DownloadTest.DownloadTestListener {
                override fun onInstantRateBit(instantRateBit: Double) {
                    activity.runOnUiThread {
                        downloadTextView.text = "$instantRateBit Mbps"
                    }
                }

                override fun onAvgRateBit(avgRateBit: Double) {
                    activity.runOnUiThread {
                        downloadTextView.text = "$avgRateBit Mbps"
                    }
                    upload(activity, dialog, view, avgRateBit, taskId)
                }

                override fun onError(error: SpeedTestException) {
                }

            }).start()
        }

        private fun upload(
            activity: Activity,
            dialog: Dialog,
            view: View,
            avgRateBitDownload: Double,
            taskId: Int
        ) {
            val uploadTextView = view.findViewById<TextView>(R.id.uploadTextView)

            UploadTest("", object : UploadTest.UploadTestListener {

                override fun onInstantRateBit(instantRateBit: Double) {
                    activity.runOnUiThread {
                        uploadTextView.text = "$instantRateBit Mbps"
                    }
                }

                override fun onAvgRateBit(avgRateBit: Double) {
                    activity.runOnUiThread {
                        uploadTextView.text = "$avgRateBit Mbps"
                    }
                    startUploadData(taskId, "Download: $avgRateBitDownload, Upload: $avgRateBit")
                    dialog.dismiss()
                }

                override fun onError(error: SpeedTestException) {
                }
            }).start()
        }

        private fun startUploadData(taskId: Int, result: String) {
            HttpManager.getInstance(activity).httpService.startTask(
                TaskGetRequest(
                    userId!!,
                    taskId,
                    result
                )
            )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Log.e(">>>>>", "startUploadData finish")
                }
        }

    }

    interface TaskStartListener {
        fun onStart()
    }
}