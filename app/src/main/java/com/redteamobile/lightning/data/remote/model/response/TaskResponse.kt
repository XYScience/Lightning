package com.redteamobile.lightning.data.remote.model.response

import android.os.Parcel
import android.os.Parcelable
import com.redteamobile.lightning.data.remote.model.bean.BannerModel
import com.redteamobile.lightning.data.remote.model.bean.TaskModel

/**
 * @author SScience
 * @description
 */
class TaskResponse : BasicResponse() {

    var obj: ArrayList<TaskModel>? = null

}