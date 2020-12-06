package com.redteamobile.lightning.data.remote.model.response

import com.redteamobile.lightning.data.remote.model.bean.EquipmentModel
import com.redteamobile.lightning.data.remote.model.bean.UserModel

/**
 * @author SScience
 * @description
 */
class UserInfoResponse : BasicResponse() {

    var result: UserInfo? = null
}

class UserInfo {
    var user : UserModel? = null
    var equipmentList : ArrayList<EquipmentModel>? = null
}
