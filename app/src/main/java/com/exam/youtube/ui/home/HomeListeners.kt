package com.exam.youtube.ui.home

import com.exam.youtube.model.PlaylistModels
import com.exam.youtube.model.VideoListResponse

interface HomeListeners {
    fun onTrendingMoviesLoaded(videoListResponse: VideoListResponse?)
    fun onPlaylistViewLoaded(videoListResponse: PlaylistModels?)
}