package com.redteamobile.ferrari.sim.constants.enums

enum class ProfileState(var value: Int) {

    DISABLED(0),
    ENABLED(1);

    fun equalsValue(value: Int?): Boolean {
        return this.value == value
    }

}
