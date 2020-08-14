package com.patrick.fittracker.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class RecordSetOrder(
    var pose_selected_title: String = "",
    var orderNum: List<String> = emptyList()
) : Parcelable