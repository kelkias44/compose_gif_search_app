package com.example.gif_search_app.domain.repository

import com.example.gif_search_app.data.model.ApiResult
import com.example.gif_search_app.domain.entity.Gif

interface GiphyRepository {
    suspend fun searchGifs(
        query: String,
        limit: Int,
        offset: Int
    ): ApiResult<List<Gif>>

    suspend fun getTrendingGifs(
        limit: Int,
        offset: Int
    ): ApiResult<List<Gif>>
}