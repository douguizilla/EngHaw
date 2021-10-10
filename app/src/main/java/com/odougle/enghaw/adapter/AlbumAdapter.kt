package com.odougle.enghaw.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.odougle.enghaw.databinding.ItemAlbumBinding
import com.odougle.enghaw.model.Album
import com.odougle.enghaw.model.AlbumHttp
import com.squareup.picasso.Picasso

class AlbumAdapter(
    private val albums: List<Album>,
    private val onItemClick: (View, Album, Int) -> Unit
) : RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val binding  = ItemAlbumBinding.inflate(
            LayoutInflater.from(parent.context),parent, false)
        val vh = AlbumViewHolder(binding)

        binding.root.setOnClickListener{ view ->
            val position = vh.adapterPosition
            val album = albums[position]
            onItemClick(view, album, position)
        }
        return vh
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album = albums[position]
        holder.run {
            Picasso.get()
                .load(AlbumHttp.BASE_URL + album.cover)
                .into(imgCover)

            txtTitle.text = album.title
            txtYear.text = album.year.toString()
        }
    }

    override fun getItemCount() = albums.size
    class AlbumViewHolder(itemAlbumBinding: ItemAlbumBinding) : RecyclerView.ViewHolder(itemAlbumBinding.root){
        var imgCover = itemAlbumBinding.imgCover
        var txtTitle = itemAlbumBinding.txtTitle
        var txtYear = itemAlbumBinding.txtYear
    }
}