package com.example.submission2.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.submission2.App
import com.example.submission2.R
import com.example.submission2.data.source.remote.response.MovieDetailResponse
import com.example.submission2.data.source.remote.response.TvDetailResponse
import com.example.submission2.databinding.ActivityDetailBinding
import com.example.submission2.utils.Constant
import javax.inject.Inject

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_KEY_MOVIE_ID = "movie_id"
        const val EXTRA_KEY_TV_SHOW_ID = "tv_show_id"
        const val EXTRA_KEY_MOVIE = "movie"
        const val EXTRA_KEY_TV_SHOW = "tv_show"
    }

    @Inject
    lateinit var detailViewModel : DetailViewModel
    private lateinit var detailBinding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailBinding.inflate(layoutInflater)
        (applicationContext as App).movieComponent.inject(this)
        setContentView(detailBinding.root)
        detailBinding.progressBar.visibility = View.VISIBLE
        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            if (bundle.containsKey(EXTRA_KEY_MOVIE)) {
                detailViewModel.getMovie(bundle.getInt(EXTRA_KEY_MOVIE)).observe(this, {
                    setView(it)
                })
            } else if (bundle.containsKey(EXTRA_KEY_TV_SHOW)) {
                detailViewModel.getTvShow(bundle.getInt(EXTRA_KEY_TV_SHOW)).observe(this, {
                    setView(it)
                })
            }
        }
        detailBinding.progressBar.visibility = View.GONE
    }

    private fun setView(movieDetailResponse: MovieDetailResponse){
        with(detailBinding){
            textViewTitle.text = movieDetailResponse.title
            textViewOverview.text = movieDetailResponse.overview
            textViewReleaseDate.text = movieDetailResponse.releaseDate
            textViewRating.text = movieDetailResponse.voteAverage.toString()
            Glide.with(this@DetailActivity)
                .load(Constant.IMAGE_BASE_URL+movieDetailResponse.posterPath)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                .error(R.drawable.ic_error)
                .into(imageBackDrop)
        }
    }

    private fun setView(tvDetailResponse: TvDetailResponse){
        with(detailBinding){
            textViewTitle.text = tvDetailResponse.name
            textViewOverview.text = tvDetailResponse.overview
            textViewReleaseDate.text = tvDetailResponse.firstAirDate
            textViewRating.text = tvDetailResponse.voteAverage.toString()
            Glide.with(this@DetailActivity)
                .load(Constant.IMAGE_BASE_URL+tvDetailResponse.posterPath)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                .error(R.drawable.ic_error)
                .into(imageBackDrop)
        }
    }
}