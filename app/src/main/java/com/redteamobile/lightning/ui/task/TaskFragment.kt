package com.redteamobile.lightning.ui.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.redteamobile.lightning.R
import com.redteamobile.lightning.data.remote.HttpManager
import com.redteamobile.lightning.data.remote.model.bean.TaskModel
import com.redteamobile.lightning.data.remote.model.response.TaskResponse
import com.redteamobile.lightning.ui.profile.adapter.TaskAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
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
            HttpManager.getInstance(a).httpService.task()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    val listTaskModel = it.data
                    listTaskModel?.let {
                        taskAdapter?.setData(it)
                    }
                }
        }
    }
}
