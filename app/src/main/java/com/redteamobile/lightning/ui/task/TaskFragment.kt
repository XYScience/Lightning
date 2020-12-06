package com.redteamobile.lightning.ui.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.redteamobile.lightning.R
import com.redteamobile.lightning.data.remote.model.bean.TaskModel
import com.redteamobile.lightning.data.remote.model.response.TaskResponse
import com.redteamobile.lightning.ui.profile.adapter.TaskAdapter
import kotlinx.android.synthetic.main.fragment_task.*


class TaskFragment : Fragment() {

    companion object {
        const val TAG = "TaskFragment"
    }

    private var taskAdapter: TaskAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_task, container, false)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    private fun initView() {
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        list_task.layoutManager = linearLayoutManager
        activity?.let { a ->
            val dividerItemDecoration = DividerItemDecoration(a, DividerItemDecoration.VERTICAL)
            list_task.addItemDecoration(dividerItemDecoration)
            taskAdapter = TaskAdapter(a)
            list_task.adapter = taskAdapter
            val listTask = ArrayList<TaskModel>()
            for (i in 0 until 10) {
                val taskModel = TaskModel()
                taskModel.id = 10000 + i
                taskModel.status = if (i % 2 == 0) 1 else 0
                taskModel.coins = 1 + i
                taskModel.createDate = 1607177704L + (i * 10)
                taskModel.result = "NONE"
                taskModel.coins = 1 + (i * 5)
                listTask.add(taskModel)
            }
            val taskModel = TaskModel()
            taskModel.id = 10000 + 66
            taskModel.status = 2
            taskModel.coins = 245
            taskModel.createDate = 1607177714L
            taskModel.result = "NONE"
            taskModel.coins = 34
            listTask.add(taskModel)

            taskAdapter?.setData(listTask)
        }
    }
}
