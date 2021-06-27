package com.example.submission2.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.submission2.data.source.TheMovieRepository
import com.example.submission2.data.source.remote.response.MovieDetailResponse
import com.example.submission2.data.source.remote.response.TvDetailResponse
import javax.inject.Inject

class DetailViewModel @Inject constructor(private val theMovieRepository: TheMovieRepository) : ViewModel() {
    fun getMovie(id: Int): LiveData<MovieDetailResponse> = theMovieRepository.getDetailMovie(id)

    fun getTvShow(id: Int): LiveData<TvDetailResponse> = theMovieRepository.getDetailTv(id)
}