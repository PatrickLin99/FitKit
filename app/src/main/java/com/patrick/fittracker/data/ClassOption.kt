package com.patrick.fittracker.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ClassOption(
    var class_title: String = "",
    var class_image: String = "",
    var class_menu: List<String> = emptyList()
) : Parcelable