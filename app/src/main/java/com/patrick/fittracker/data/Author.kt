package com.patrick.fittracker.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Author(
    val id: String = "",
    val name: String = "",
    val email: String = ""
) : Parcelable