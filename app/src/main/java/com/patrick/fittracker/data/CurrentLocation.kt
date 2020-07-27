package com.patrick.fittracker.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CurrentLocation(
    var latitude: Double? = null,
    var longitude: Double? = null
) : Parcelable