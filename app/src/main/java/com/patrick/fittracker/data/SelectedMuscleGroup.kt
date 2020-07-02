package com.patrick.fittracker.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SelectedMuscleGroup(
    var menu: List<String> = emptyList()
) : Parcelable