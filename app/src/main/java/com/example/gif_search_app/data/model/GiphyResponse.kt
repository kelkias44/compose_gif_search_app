package com.example.gif_search_app.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class GiphyResponse(
    @SerializedName("data")
    val data: List<GifData>,
    @SerializedName("meta")
    val meta: Meta,
    @SerializedName("pagination")
    val pagination: Pagination? = null
)


data class Meta(
    @SerializedName("status")
    val status: Int,
    @SerializedName("msg")
    val msg: String,
    @SerializedName("response_id")
    val responseId: String
)

data class Pagination(
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("count")
    val count: Int,
    @SerializedName("offset")
    val offset: Int
)

data class GifData(
    @SerializedName("id")
    val id: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("embed_url")
    val embedUrl: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("rating")
    val rating: String,
    @SerializedName("images")
    val images: Images
)

@Parcelize
data class Images(
    @SerializedName("downsized")
    val downsized: Image,
    @SerializedName("preview_gif")
    val previewGif: Image,
): Parcelable

@Parcelize
data class Image(
    @SerializedName("height")
    val height: String,
    @SerializedName("width")
    val width: String,
    @SerializedName("url")
    val url: String,
): Parcelable

