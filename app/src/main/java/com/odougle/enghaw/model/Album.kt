package com.odougle.enghaw.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Album (
    @SerializedName("titulo")
    val title: String,
    @SerializedName("capa")
    val cover: String,
    @SerializedName("capa_big")
    val coverBig: String,
    @SerializedName("ano")
    val year: Int,
    @SerializedName("gravadora")
    val recordingCompany: String,
    @SerializedName("formacao")
    val formattion: List<String>,
    @SerializedName("tracks")
    val tracks: List<String>
) : Parcelable