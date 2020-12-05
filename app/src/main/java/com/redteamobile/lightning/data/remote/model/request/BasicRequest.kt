package com.redteamobile.lightning.data.remote.model.request

import com.redteamobile.lightning.util.Util

/**
 * @author SScience
 * @description
 */
open class BasicRequest {
    var clientId: Int = 2001

    var meta: MetaObject =
        MetaObject()
}

class MetaObject{
    var currentTime: Long = System.currentTimeMillis()

    var requestId: String = Util.generateRequestId()
}