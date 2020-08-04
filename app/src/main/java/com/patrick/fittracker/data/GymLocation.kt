package com.patrick.fittracker.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GymLocation(
    var name: String = "",
    var locationN: Double = 0.0,
    var locationE: Double = 0.0
) : Parcelable