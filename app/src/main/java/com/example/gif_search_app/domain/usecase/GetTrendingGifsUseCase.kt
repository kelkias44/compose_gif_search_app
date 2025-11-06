package com.example.gif_search_app.domain.usecase

import com.example.gif_search_app.data.model.ApiResult
import com.example.gif_search_app.domain.entity.Gif
import com.example.gif_search_app.domain.repository.GiphyRepository
import javax.inject.Inject

class GetTrendingGifsUseCase @Inject constructor(
    private val repository: GiphyRepository
) {
    suspend operator fun invoke(
        limit: Int = 25,
        offset: Int = 0
    ): ApiResult<List<Gif>> {
        return repository.getTrendingGifs(limit, offset)
    }
}


