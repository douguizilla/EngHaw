package com.odougle.enghaw

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.odougle.enghaw.databinding.ActivityDetailsBinding
import com.odougle.enghaw.model.Album
import com.odougle.enghaw.model.AlbumHttp
import com.squareup.picasso.Picasso
import java.lang.StringBuilder

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val album = intent.getParcelableExtra<Album>(EXTRA_ALBUM)
        if(album != null){
            loadCover(album)
            initTitlebar(album.title)
            fillFields(album)
        }
    }

    private fun loadCover(album: Album) {
        Picasso.get()
            .load(AlbumHttp.BASE_URL + album.coverBig)
            .into(binding.imgCover)
    }

    private fun initTitlebar(title: String) {
        binding.apply {
            setSupportActionBar(toolbar)
            if(appBar != null){
                if(appBar?.layoutParams is CoordinatorLayout.LayoutParams){
                    val lp = appBar?.layoutParams as CoordinatorLayout.LayoutParams
                    lp.height = resources.displayMetrics.widthPixels
                }
            }
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            if(collapseToolbar != null){
                supportActionBar?.setDisplayShowTitleEnabled(true)
                collapseToolbar?.title = title
            }else{
                supportActionBar?.setDisplayShowTitleEnabled(false)
            }
        }
    }

    private fun fillFields(album: Album) {
        binding.contentDetails.apply {
            txtTitle.text = album.title
            txtYear.text = album.year.toString()
            txtRecordingCompany.text = album.recordingCompany
            var sb = StringBuilder()
            for(member in album.formattion) {
                if(sb.isNotEmpty()) sb.append('\n')
                sb.append(member)
            }
            txtFormation.text = sb.toString()
            sb = StringBuilder()
            album.tracks?.forEachIndexed { index, track ->
                if(sb.isNotEmpty()) sb.append('\n')
                sb.append(index + 1).append(". ").append(track)
            }
            txtSongs.text = sb.toString()
        }
    }

    companion object{
        const val EXTRA_ALBUM = "album"
        fun start(context: Context, album: Album){
            context.startActivity(Intent(context, DetailsActivity::class.java).apply{
                putExtra(EXTRA_ALBUM, album)
            })
        }
    }




}