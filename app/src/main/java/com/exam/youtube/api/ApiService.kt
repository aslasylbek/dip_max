package com.exam.youtube.api

import com.exam.youtube.model.PlaylistModels
import com.exam.youtube.model.VideoListResponse
import com.exam.youtube.utils.TRENDING
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(TRENDING)
    fun getVideos(
        @Query("part") part: String,
        @Query("chart") chart: String,
        @Query("regionCode") regionCode: String,
        @Query("maxResults") maxResults: Int,
        @Query("key") key: String
    ): Call<VideoListResponse>

    @GET("playlistItems")
    fun getPlaylist(
        @Query("part") part: String,
        @Query("maxResults") maxResults: Int,
        @Query("playlistId") playlistId: String,
        @Query("key") key: String
    ): Call<PlaylistModels>

}