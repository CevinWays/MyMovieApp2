package com.example.submission2.source

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.submission2.data.Movie
import com.example.submission2.data.TvShow
import com.example.submission2.data.source.TheMovieDataSource
import com.example.submission2.data.source.remote.RemoteDataSource
import com.example.submission2.data.source.remote.response.MovieDetailResponse
import com.example.submission2.data.source.remote.response.MovieResponse
import com.example.submission2.data.source.remote.response.TvDetailResponse
import com.example.submission2.data.source.remote.response.TvResponse
import retrofit2.Call
import retrofit2.Response

class FakeTheMovieRepository(private val remoteDataSource: RemoteDataSource) : TheMovieDataSource {
    companion object {
        private const val TAG = "FakeTheMovieRepository"
    }

    override fun getAllMovie(page: Int): LiveData<List<Movie>> {
        val movieResults = MutableLiveData<List<Movie>>()
        remoteDataSource.getAll(
            object : TheMovieDataSource.LoadMoviesCallback {
                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    if (response.isSuccessful) {
                        val movieList = ArrayList<Movie>()
                        for (movieResponse in response.body()?.results ?: listOf()) {
                            movieList.add(Movie(movieResponse))
                        }
                        movieResults.postValue(movieList)
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            },
            page
        )
        return movieResults
    }

    override fun getAllTvShow(page: Int): LiveData<List<TvShow>> {
        val tvResults = MutableLiveData<List<TvShow>>()
        remoteDataSource.getAll(object : TheMovieDataSource.LoadTvCallback {
            override fun onResponse(
                call: Call<TvResponse>,
                response: Response<TvResponse>
            ) {
                if (response.isSuccessful) {
                    val tvList = ArrayList<TvShow>()
                    for (tvResponse in response.body()?.results ?: listOf()) {
                        tvList.add(TvShow(tvResponse))
                    }
                    tvResults.postValue(tvList)
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<TvResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        }, page)
        return tvResults
    }

    override fun getDetailMovie(id: Int): LiveData<MovieDetailResponse> {
        val movieResult = MutableLiveData<MovieDetailResponse>()

        remoteDataSource.getDetail(object : TheMovieDataSource.LoadDetailMovieCallback {
            override fun onResponse(
                call: Call<MovieDetailResponse>,
                response: Response<MovieDetailResponse>
            ) {
                if (response.isSuccessful) {
                    movieResult.postValue(response.body())
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MovieDetailResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        }, id)
        return movieResult
    }

    override fun getDetailTv(id: Int): LiveData<TvDetailResponse> {
        val tvResult = MutableLiveData<TvDetailResponse>()

        remoteDataSource.getDetail(object : TheMovieDataSource.LoadDetailTvCallback {
            override fun onResponse(
                call: Call<TvDetailResponse>,
                response: Response<TvDetailResponse>
            ) {
                if (response.isSuccessful) {
                    tvResult.postValue(response.body())
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<TvDetailResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        }, id)
        return tvResult
    }
}