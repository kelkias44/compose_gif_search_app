package com.example.gif_search_app.data.mapper

import com.example.gif_search_app.data.model.GifData
import com.example.gif_search_app.domain.entity.Gif

fun GifData.toDomain(): Gif {
    return Gif(
        id = id,
        type = type,
        url = url,
        embedUrl = embedUrl,
        title = title,
        rating = rating,
        images = images
    )
}