package com.patrick.fittracker.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Cardio(
    var cardio_title: String = "",
    var cardio_image: String = "",
    var cardio_unknown: String = ""
) : Parcelable