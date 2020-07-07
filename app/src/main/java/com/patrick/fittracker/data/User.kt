package com.patrick.fittracker.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val id: String = "PeiStar",
    val name: String = "PeiStar"
) : Parcelable