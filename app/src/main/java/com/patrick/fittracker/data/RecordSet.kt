package com.patrick.fittracker.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class RecordSet(
    var type: String,
    var createdTime: Long = 0,
    var record_detail: Detail? = null
) : Parcelable

@Parcelize
data class Detail(
    var orderSet: Long = 0,
    var weight: Long = 0,
    var recps: Long = 0
) : Parcelable

