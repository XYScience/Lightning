package com.redteamobile.lightning.data.local.sim.model

import android.os.Parcel
import android.os.Parcelable

/**
 * @author SScience
 * @description
 */
class ProfileInfos() : Parcelable {

    var simSlotIndex: Int? = null
    var iccId: String? = null
    var mccString: String? = null
    var mncString: String? = null
    var netType: String? = null
    var phoneType: String? = null
    var carrierName: CharSequence? = null
    var subscriptionId: Int? = null
    var displayName: CharSequence? = null
    //    DISABLED(0),
    //    ENABLED(1);
    //    UNDOWNLOAD(2);
    var profileState: Int? = null
    var ac: String? = null

    constructor(parcel: Parcel) : this() {
        simSlotIndex = parcel.readValue(Int::class.java.classLoader) as? Int
        iccId = parcel.readString()
        mccString = parcel.readString()
        mncString = parcel.readString()
        netType = parcel.readString()
        phoneType = parcel.readString()
        carrierName = parcel.readString()
        subscriptionId = parcel.readValue(Int::class.java.classLoader) as? Int
        displayName = parcel.readString()
        profileState = parcel.readValue(Int::class.java.classLoader) as? Int
        ac = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(simSlotIndex)
        parcel.writeString(iccId)
        parcel.writeString(mccString)
        parcel.writeString(mncString)
        parcel.writeString(netType)
        parcel.writeString(phoneType)
        parcel.writeString(carrierName?.toString())
        parcel.writeValue(subscriptionId)
        parcel.writeString(displayName?.toString())
        parcel.writeValue(profileState)
        parcel.writeString(ac)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProfileInfos> {
        override fun createFromParcel(parcel: Parcel): ProfileInfos {
            return ProfileInfos(parcel)
        }

        override fun newArray(size: Int): Array<ProfileInfos?> {
            return arrayOfNulls(size)
        }
    }
}