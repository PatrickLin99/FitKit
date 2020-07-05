package com.patrick.fittracker.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class RecordSetOrder(
    var unknown: String = "",
    var orderNum: List<String> = emptyList()
) : Parcelable