package com.patrick.fittracker.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Chest(
    var barbell_upper_chest: String = "",
    var barbell_mid_chest: String = "",
    var barbell_lower_chest: String = ""
) : Parcelable