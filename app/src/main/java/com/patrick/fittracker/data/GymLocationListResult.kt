package com.patrick.fittracker.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize


@Parcelize
data class  GymLocationListResult(
    val error: String? = null,
    val html_attributions: Array<String>? = null,
    val results: List<DetailResults>? = null,
    val status: String? = null
    ): Parcelable

@Parcelize
data class  DetailResults(
    val business_status: String? = null,
    val geometry: GeometryDeatil? = null,
    val icon: String? = null,
    val id: String? = null,
    val name: String? = null,
    val opening_hours: OpeningHours? = null,
    val photos: Array<PhotoDetail>? = null,
    val place_id: String? = null,
    val reference: String? = null,
    val scope: String? = null,
    val types: List<String>? = null,
    val vicinity: String? = null
    ): Parcelable

@Parcelize
data class  GeometryDeatil(
    val location: InnerLocationDeatil? = null,
    val viewport: ViewPortDetail? = null
): Parcelable

@Parcelize
data class  InnerLocationDeatil(
    val lat: String? = null,
    val lng: String? = null
): Parcelable

@Parcelize
data class  ViewPortDetail(
    val northeast: Northeast? = null,
    val southwest: Southwest? = null
): Parcelable


@Parcelize
data class  Northeast(
    val lat: String? = null,
    val lng: String? = null
): Parcelable


@Parcelize
data class  Southwest(
    val lat: String? = null,
    val lng: String? = null
): Parcelable

@Parcelize
data class  PhotoDetail(
    val height: String? = null,
    val html_attributions: Array<String>? = null,
    val photo_reference: String? = null,
    val width: Int? = 0
): Parcelable

@Parcelize
data class  OpeningHours(
    val open_now: Boolean? = null
): Parcelable
