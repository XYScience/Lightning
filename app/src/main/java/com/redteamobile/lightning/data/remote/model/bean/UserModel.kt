package com.redteamobile.lightning.data.remote.model.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * @author SScience
 * @description
 */
class UserModel() : Parcelable {

    var id: Int? = null
    var name: String? = null
    var password: String? = null
    var coins: Int? = null
    var createDate: String? = null
    var nickName: String? = null
    var telephone: String? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        name = parcel.readString()
        password = parcel.readString()
        coins = parcel.readValue(Int::class.java.classLoader) as? Int
        createDate = parcel.readString()
        nickName = parcel.readString()
        telephone = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(name)
        parcel.writeString(password)
        parcel.writeValue(coins)
        parcel.writeString(createDate)
        parcel.writeString(nickName)
        parcel.writeString(telephone)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserModel> {
        override fun createFromParcel(parcel: Parcel): UserModel {
            return UserModel(parcel)
        }

        override fun newArray(size: Int): Array<UserModel?> {
            return arrayOfNulls(size)
        }
    }

}