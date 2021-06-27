package com.example.submission2.movie

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.submission2.R
import com.example.submission2.data.Movie
import com.example.submission2.databinding.ItemMovieBinding
import com.example.submission2.detail.DetailActivity
import com.example.submission2.utils.Constant

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    private var listMovie = ArrayList<Movie>()

    fun setMovie(movies: List<Movie>?) {
        if (movies == null) return
        this.listMovie.addAll(movies)
    }

    class ViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie:Movie){
            with(binding){
                textViewTitle.text = movie.title
                textViewOverview.text = movie.overview
                textViewRating.text = movie.voteAverage.toString()
                textViewReleaseDate.text = movie.releaseDate
                Glide.with(itemView.context)
                    .load(Constant.IMAGE_BASE_URL+movie.posterPath)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                    .error(R.drawable.ic_error)
                    .into(imageViewThumbnail)
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_KEY_MOVIE, movie.id)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemMovieBinding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemMovieBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = listMovie[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int = listMovie.size
}