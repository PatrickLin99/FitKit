package com.patrick.fittracker.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class InsertRecord(
    var name: String = "",
    var fitDetail: List<FitDetail>,
    var createdTime: String = ""
) : Parcelable


@Parcelize
data class FitDetail (
    var orderSet: Long = 0,
    var weight: Long = 5,
    var count: Long = 0
    ) : Parcelable