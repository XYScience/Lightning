package com.redteamobile.lightning.ui.task

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.redteamobile.lightning.R
import com.redteamobile.lightning.data.remote.HttpManager
import com.redteamobile.lightning.data.remote.model.bean.TaskModel
import com.redteamobile.lightning.data.remote.model.response.TaskResponse
import com.redteamobile.lightning.ui.profile.adapter.TaskAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_task.*


class TaskFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

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
        swipeRefresh.setOnRefreshListener(this)
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        list_task.layoutManager = linearLayoutManager
        val dividerItemDecoration =
            DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL)
        list_task.addItemDecoration(dividerItemDecoration)
        taskAdapter = TaskAdapter(requireActivity())
        list_task.adapter = taskAdapter
        onRefresh()
    }

    override fun onRefresh() {
        swipeRefresh.isRefreshing = true
        getData()
    }

    private fun getData() {
        HttpManager.getInstance(requireActivity()).httpService.task()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                val listTaskModel = it.data
                val list = ArrayList<TaskModel>()

                listTaskModel?.forEach { task ->
                    if (task.status == 1) {
                        list.add(0, task)
                    } else {
                        list.add(task)
                    }
                    return@forEach
                }
                Log.e(TAG, "getData list: ${list.size}")
                taskAdapter?.setData(list)
                swipeRefresh.isRefreshing = false
            }
    }
}
