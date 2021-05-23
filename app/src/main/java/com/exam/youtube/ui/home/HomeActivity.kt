package com.exam.youtube.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.exam.youtube.R
import com.exam.youtube.adapter.VideoListAdapter
import com.exam.youtube.model.Items
import com.exam.youtube.model.PlaylistModels
import com.exam.youtube.model.VideoListResponse
import com.exam.youtube.ui.PdfReaderActivity
import com.exam.youtube.ui.player.VideoPlayerActivity
import com.exam.youtube.utils.VIDEO_ITEM
import com.exam.youtube.utils.isNetworkConnected
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(), HomeListeners, VideoListAdapter.OnItemClickListener {

    private var mVideoItemsList = ArrayList<Items>()
    private val mVideoListAdapter = VideoListAdapter(this)
    private val presenter = HomePresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initSwipeRefresh()
        initRecyclerView()
        getTrendingVideos()


    }

    private fun initSwipeRefresh(){
        swipeRefresh.setOnRefreshListener {
            getTrendingVideos()
        }
        swipeRefresh.isRefreshing = true
    }

    private fun initRecyclerView(){
        val linearLayoutManager = LinearLayoutManager(this)
        video_rv.adapter = mVideoListAdapter
        video_rv.layoutManager = linearLayoutManager
    }

    private fun getTrendingVideos(){
        if (isNetworkConnected(this)){
            swipeRefresh.isRefreshing = true
            presenter.getPlaylist()
        }else{
            swipeRefresh.isRefreshing = false
            logo_iv.visibility = View.VISIBLE
            val snackbar = Snackbar.make(swipeRefresh,R.string.no_internet,Snackbar.LENGTH_LONG)
            snackbar.setBackgroundTint(resources.getColor(R.color.black))
            snackbar.show()
        }
    }

    private fun updateUi(response: PlaylistModels?){
        swipeRefresh.isRefreshing = false
        if (response != null){
            mVideoItemsList = response.items as ArrayList<Items>
            mVideoListAdapter.setItems(mVideoItemsList)
            mVideoListAdapter.notifyDataSetChanged()
            logo_iv.visibility = View.INVISIBLE
            Toast.makeText(this, R.string.videos_updated, Toast.LENGTH_SHORT).show()
        }else{
            logo_iv.visibility = View.VISIBLE
            Toast.makeText(this, R.string.network_request_failed, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onTrendingMoviesLoaded(videoListResponse: VideoListResponse?) {
        //updateUi(videoListResponse)
    }

    override fun onPlaylistViewLoaded(videoListResponse: PlaylistModels?) {
        updateUi(videoListResponse)
    }

    override fun onItemClicked(position: Int) {
        val item = mVideoItemsList[position]
        val intent = Intent(this, VideoPlayerActivity::class.java)
        intent.putExtra(VIDEO_ITEM,item)
        startActivity(intent)
    }
}
