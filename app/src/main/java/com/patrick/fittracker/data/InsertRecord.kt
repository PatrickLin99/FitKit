package com.patrick.fittracker.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class InsertRecord(
    var name: String = "",
    var fitDetail: List<FitDetail> = listOf(),
    var createdTime: Long = 0,
    var recordImage: String = ""
) : Parcelable


@Parcelize
data class FitDetail (
    var orderSet: Long = 0,
    var weight: Long = 5,
    var count: Long = 0
) : Parcelable