package com.example.gif_search_app.data.repository

import com.example.gif_search_app.BuildConfig
import com.example.gif_search_app.data.mapper.toDomain
import com.example.gif_search_app.data.model.ApiResult
import com.example.gif_search_app.data.model.GiphyResponse
import com.example.gif_search_app.data.remote.GiphyApi
import com.example.gif_search_app.domain.entity.Gif
import com.example.gif_search_app.domain.repository.GiphyRepository
import retrofit2.Response
import javax.inject.Inject

class GifRepositoryImpl @Inject constructor(
     private val giphyApi: GiphyApi
) : GiphyRepository {
    val apiKey = BuildConfig.GIPHY_API_KEY
    override suspend fun searchGifs(
        query: String,
        limit: Int,
        offset: Int
    ): ApiResult<List<Gif>> {
        try{
            val response = giphyApi.searchGifs(apiKey.toString(), query, limit, offset)
            return handleResponse(response)
        }catch (e: Exception){
            return ApiResult.Error(Exception("Unable to connect. Check your connection!"))
        }

    }

    override suspend fun getTrendingGifs(
        limit: Int,
        offset: Int
    ): ApiResult<List<Gif>> {
        try{
            val response = giphyApi.getTrendingGifs(apiKey.toString(), limit, offset)
            return handleResponse(response)
        }catch (e: Exception){
            return ApiResult.Error(Exception("Unable to connect. Check your connection!"))
        }
    }


    private fun handleResponse(response: Response<GiphyResponse>): ApiResult<List<Gif>> {
        val authResponse = response.body()
        return if (authResponse != null) {
            if (response.isSuccessful){
                ApiResult.Success(authResponse.data.map { it.toDomain() })
            } else{
                ApiResult.Error(Exception(authResponse.meta.msg))
            }
        } else {
            ApiResult.Error(Exception("Invalid response from server"))
        }
    }
}