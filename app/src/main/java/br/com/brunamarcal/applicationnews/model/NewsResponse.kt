package br.com.brunamarcal.applicationnews.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewsResponse (

    @SerializedName("articles")
    val newsResult: List<NewsResult>

): Parcelable