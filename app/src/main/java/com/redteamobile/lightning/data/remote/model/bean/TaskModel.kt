package com.redteamobile.lightning.data.remote.model.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * @author SScience
 * @description
 */
class TaskModel() : Parcelable {

    var id: Int? = null  //唯一编号
    var name: String? = null  //任务名称
    var status: Int? = null  //状态：1用户可接单状态 0任务已经结束 2商家取消任务
    var createDate: String? = null  //创建时间
    var coins: Int? = null  //本次任务金额，单位分
    var userId: Int? = null  //执行任务的用户id，接单之前该字段为空
    var merchantId: Int? = null  //接单的设备号
    var result: String? = null  //任务执行结果
    var command: String? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        name = parcel.readString()
        status = parcel.readValue(Int::class.java.classLoader) as? Int
        createDate = parcel.readString()
        coins = parcel.readValue(Int::class.java.classLoader) as? Int
        userId = parcel.readValue(Int::class.java.classLoader) as? Int
        merchantId = parcel.readValue(Int::class.java.classLoader) as? Int
        result = parcel.readString()
        command = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(name)
        parcel.writeValue(status)
        parcel.writeString(createDate)
        parcel.writeValue(coins)
        parcel.writeValue(userId)
        parcel.writeValue(merchantId)
        parcel.writeString(result)
        parcel.writeString(command)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TaskModel> {
        override fun createFromParcel(parcel: Parcel): TaskModel {
            return TaskModel(parcel)
        }

        override fun newArray(size: Int): Array<TaskModel?> {
            return arrayOfNulls(size)
        }
    }

}