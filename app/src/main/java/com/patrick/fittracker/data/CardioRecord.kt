package com.patrick.fittracker.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class CardioRecord(
    var name: String = "",
    var createdTime: Long = 0,
    var duration: Long = 0,
    var burnFat: Long = 0
) : Parcelable

