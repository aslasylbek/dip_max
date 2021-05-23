package com.exam.youtube.ui.home

import android.util.Log
import com.exam.youtube.api.ApiClient
import com.exam.youtube.model.PlaylistModels
import com.exam.youtube.model.VideoListResponse
import com.exam.youtube.utils.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomePresenter(val homeListeners: HomeListeners) {
    private val TAG = "HomePresenter"


     fun getTrendingMovies() {
        Log.i(TAG, "Api request >>>>>>> getVideos")

        val call = ApiClient.getTrendingVideos(
            PART_QUERY_VALUE,
            CHART_QUERY_VALUE,
            REGION_CODE_QUERY_VALUE,
            MAX_RESULT_QUERY_VALUE,
            GOOGLE_API_KEY
        )
         call.enqueue(object : Callback<VideoListResponse> {
            override fun onResponse(call: Call<VideoListResponse>, response: Response<VideoListResponse>) {
                Log.i(TAG, "Get videos Response : $response ")
                if (response.isSuccessful)
                    homeListeners.onTrendingMoviesLoaded(response.body())
            }
            override fun onFailure(call: Call<VideoListResponse>, t: Throwable) {
                Log.e(TAG, "error loading videos : $t")
                homeListeners.onTrendingMoviesLoaded(null)
            }
        })
    }

    fun getPlaylist() {
        Log.i(TAG, "Api request >>>>>>> getVideos")

        val call = ApiClient.getPlaylistVideos(
                PART_QUERY_VALUE,
                MAX_RESULT_QUERY_VALUE,
                PLAYLIST_ID,
                GOOGLE_API_KEY
        )
        call.enqueue(object : Callback<PlaylistModels> {
            override fun onResponse(call: Call<PlaylistModels>, response: Response<PlaylistModels>) {
                Log.i(TAG, "Get videos Response : $response ")
                if (response.isSuccessful)
                    homeListeners.onPlaylistViewLoaded(response.body())
            }
            override fun onFailure(call: Call<PlaylistModels>, t: Throwable) {
                Log.e(TAG, "error loading videos : $t")
                homeListeners.onPlaylistViewLoaded(null)
            }
        })
    }
}