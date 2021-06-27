package com.example.submission2.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.submission2.data.Movie
import com.example.submission2.data.source.TheMovieRepository
import javax.inject.Inject

class MovieViewModel @Inject constructor(private val theMovieRepository: TheMovieRepository) : ViewModel() {
    private var page = 0

    fun getListMovie(): LiveData<List<Movie>> {
        page++
        return theMovieRepository.getAllMovie(page)
    }
}