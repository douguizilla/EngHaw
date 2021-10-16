package com.odougle.enghaw

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.odougle.enghaw.adapter.AlbumAdapter
import com.odougle.enghaw.model.Album
import com.odougle.enghaw.model.AlbumHttp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AlbumListWebFragment : AlbumListBaseFragment() {
    private var albums: List<Album>? =  null
    private var downloadJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.swpRefresh.setOnRefreshListener {
            loadAlbumsAsync()
        }

        binding.rvAlbums.run {
            tag = "web"
            setHasFixedSize(true)
            val orientation = resources.configuration.orientation
            layoutManager = if(orientation == Configuration.ORIENTATION_PORTRAIT){
                LinearLayoutManager(activity)
            }else{
                GridLayoutManager(activity, 2)
            }
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            itemAnimator = DefaultItemAnimator()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if(albums == null){
            if(downloadJob?.isActive == true){
                showProgress(true)
            }else{
                loadAlbumsAsync()
            }
        }else{
            updateList()
        }
    }

    private fun loadAlbumsAsync() {
        downloadJob = launch {
            showProgress(true)
            albums = withContext(Dispatchers.IO){
                AlbumHttp.loadAlbuns()?.toList()
            }
            showProgress(false)
            updateList()
            downloadJob = null
        }
    }

    private fun onItemClick(v: View, album: Album, position: Int){
        context?.run {
            DetailsActivity.start(this, album)
        }
    }

    private fun updateList() {
        val list = albums ?: emptyList()
        binding.rvAlbums.adapter = AlbumAdapter(list, this::onItemClick)
    }

    private fun showProgress(show: Boolean) {
        binding.swpRefresh.post{
            binding.swpRefresh.isRefreshing = show
        }
    }
}