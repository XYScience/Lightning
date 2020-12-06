package com.redteamobile.lightning.data.remote.model.response

import com.redteamobile.lightning.data.remote.model.bean.TaskModel

/**
 * @author SScience
 * @description
 */
class TaskResponse : BasicResponse() {

    var data: ArrayList<TaskModel>? = null

    var count : Int? = null
}