package com.example.submission2.data.source

import androidx.lifecycle.LiveData
import com.example.submission2.data.Movie
import com.example.submission2.data.TvShow
import com.example.submission2.data.source.remote.response.MovieDetailResponse
import com.example.submission2.data.source.remote.response.MovieResponse
import com.example.submission2.data.source.remote.response.TvDetailResponse
import com.example.submission2.data.source.remote.response.TvResponse
import retrofit2.Callback

interface TheMovieDataSource {
    fun getAllMovie(page: Int): LiveData<List<Movie>>

    fun getAllTvShow(page: Int): LiveData<List<TvShow>>

    fun getDetailMovie(id: Int): LiveData<MovieDetailResponse>

    fun getDetailTv(id: Int): LiveData<TvDetailResponse>

    interface LoadMoviesCallback : Callback<MovieResponse>

    interface LoadTvCallback : Callback<TvResponse>

    interface  LoadDetailMovieCallback : Callback<MovieDetailResponse>

    interface LoadDetailTvCallback : Callback<TvDetailResponse>
}