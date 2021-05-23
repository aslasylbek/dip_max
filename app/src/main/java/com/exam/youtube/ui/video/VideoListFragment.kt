package com.exam.youtube.ui.video

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.exam.youtube.R
import com.exam.youtube.adapter.VideoListAdapter
import com.exam.youtube.model.Items
import com.exam.youtube.model.PlaylistModels
import com.exam.youtube.model.VideoListResponse
import com.exam.youtube.ui.PdfReaderActivity
import com.exam.youtube.ui.home.HomeListeners
import com.exam.youtube.ui.home.HomePresenter
import com.exam.youtube.ui.player.VideoPlayerActivity
import com.exam.youtube.utils.VIDEO_ITEM
import com.exam.youtube.utils.isNetworkConnected
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_home.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [VideoListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class VideoListFragment : Fragment(), HomeListeners, VideoListAdapter.OnItemClickListener {


    private var mVideoItemsList = ArrayList<Items>()
    private val mVideoListAdapter = VideoListAdapter( this)
    private val presenter = HomePresenter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSwipeRefresh()
        initRecyclerView()
        getTrendingVideos()
    }

    private fun initSwipeRefresh() {
        swipeRefresh.setOnRefreshListener {
            getTrendingVideos()
        }
        swipeRefresh.isRefreshing = true
    }

    private fun initRecyclerView() {
        video_rv.adapter = mVideoListAdapter
    }

    private fun getTrendingVideos() {
        if (isNetworkConnected(requireContext())) {
            swipeRefresh.isRefreshing = true
            presenter.getPlaylist()
        } else {
            swipeRefresh.isRefreshing = false
            logo_iv.visibility = View.VISIBLE
            val snackbar = Snackbar.make(swipeRefresh, R.string.no_internet, Snackbar.LENGTH_LONG)
            snackbar.setBackgroundTint(resources.getColor(R.color.black))
            snackbar.show()
        }
    }

    private fun updateUi(response: PlaylistModels?) {
        swipeRefresh.isRefreshing = false
        if (response != null) {
            mVideoItemsList = response.items as ArrayList<Items>
            mVideoListAdapter.setItems(mVideoItemsList)
            mVideoListAdapter.notifyDataSetChanged()
            logo_iv.visibility = View.INVISIBLE
            Toast.makeText(requireContext(), R.string.videos_updated, Toast.LENGTH_SHORT).show()
        } else {
            logo_iv.visibility = View.VISIBLE
            Toast.makeText(requireContext(), R.string.network_request_failed, Toast.LENGTH_SHORT)
                .show()
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
        val intent = Intent(requireContext(), VideoPlayerActivity::class.java)
        intent.putExtra(VIDEO_ITEM, item)
        startActivity(intent)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video_list, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment VideoListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            VideoListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}