package com.patrick.fittracker.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AddTrainingRecord(
    var category_title: String = "",
    var id: String = "",
    var createdTime: Long = 0,
    var order_title: Long = 1,
    var weight: Long = 5,
    var orderSet: Long = 0,
    var duration: Long = 0,
    var classField: String = "",
    var burnFat: Long = 0,
    var user: User? = null
) : Parcelable