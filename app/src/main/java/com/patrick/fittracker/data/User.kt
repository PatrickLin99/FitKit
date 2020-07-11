package com.patrick.fittracker.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var id: String = "",
    var name: String = "",
    var email: String = "",
    var createdTime: Long = 0,
    var userProfile: UserProfile? = null
) : Parcelable

@Parcelize
data class UserProfile(
    var id: String = "",
    var createdTime: Long = 0,
    var info_name: String = "",
    var info_weight : Long = 0,
    var info_height : Long = 0,
    var info_BMI: Long = 0,
    var info_bodyFat : Long = 0,
    var info_age : Long = 0,
    var info_image: String = ""
) : Parcelable