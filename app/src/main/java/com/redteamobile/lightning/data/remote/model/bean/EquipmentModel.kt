package com.redteamobile.lightning.data.remote.model.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * @author SScience
 * @description
 */
class EquipmentModel() : Parcelable {

    var id: Int? = null
    var name: String? = null
    var location: String? = null
    var longitude: String? = null
    var latitude: String? = null
    var userId: Int? = null
    var createDate: String? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        name = parcel.readString()
        location = parcel.readString()
        longitude = parcel.readString()
        latitude = parcel.readString()
        userId = parcel.readValue(Int::class.java.classLoader) as? Int
        createDate = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(name)
        parcel.writeString(location)
        parcel.writeValue(longitude)
        parcel.writeString(latitude)
        parcel.writeValue(userId)
        parcel.writeString(createDate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EquipmentModel> {
        override fun createFromParcel(parcel: Parcel): EquipmentModel {
            return EquipmentModel(parcel)
        }

        override fun newArray(size: Int): Array<EquipmentModel?> {
            return arrayOfNulls(size)
        }
    }

}