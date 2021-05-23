package com.exam.youtube.api

import com.exam.youtube.model.PlaylistModels
import com.exam.youtube.model.VideoListResponse
import com.exam.youtube.utils.BASE_URL
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private val mApiService: ApiService

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        mApiService = retrofit.create(ApiService::class.java)
    }

    fun getTrendingVideos(
        part: String, chart: String, regionCode: String,
        maxResults: Int, apiKy: String
    ): Call<VideoListResponse> {
        return mApiService.getVideos(part, chart, regionCode, maxResults, apiKy)
    }

    fun getPlaylistVideos(
            part: String, maxResults: Int, playlistId: String,  apiKy: String
    ): Call<PlaylistModels> {
        return mApiService.getPlaylist(part, maxResults, playlistId, apiKy)
    }
}
