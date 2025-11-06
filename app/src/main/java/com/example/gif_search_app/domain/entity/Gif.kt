package com.example.gif_search_app.domain.entity

import android.os.Parcelable
import com.example.gif_search_app.data.model.Images
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Gif(
    val id: String,
    val type: String,
    val url: String,
    val embedUrl: String,
    val title: String,
    val rating: String,
    val images: Images
) : Parcelable