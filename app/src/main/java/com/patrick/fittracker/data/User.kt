package com.patrick.fittracker.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var id: String = "PeiStar",
    var name: String = "PeiStar",
//    var info_bodyFat : Long = 0,
    var info_BMI: Long = 0,
    var info_weight : Long = 0,
    var info_height : Long = 0,
    var info_age : Long = 0,
    var info_image: String = ""
) : Parcelable