package br.com.brunamarcal.applicationnews.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SourcesResponse (

    @SerializedName("sources")
    val response: List<SourcesResult>

): Parcelable


