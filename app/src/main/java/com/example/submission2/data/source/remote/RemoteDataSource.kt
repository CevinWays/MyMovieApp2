package com.example.submission2.data.source.remote
import com.example.submission2.data.source.TheMovieDataSource
import com.example.submission2.utils.retrofit.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {
    fun getAll(callback: TheMovieDataSource.LoadMoviesCallback, page:Int) = apiService.getMovie(page).enqueue(callback)

    fun getAll(callback: TheMovieDataSource.LoadTvCallback, page: Int) =  apiService.getTv(page).enqueue(callback)

    fun getDetail(callback: TheMovieDataSource.LoadDetailMovieCallback, id: Int) = apiService.getDetailMovie(id).enqueue(callback)

    fun getDetail(callback: TheMovieDataSource.LoadDetailTvCallback, id: Int) = apiService.getDetailTv(id).enqueue(callback)
}