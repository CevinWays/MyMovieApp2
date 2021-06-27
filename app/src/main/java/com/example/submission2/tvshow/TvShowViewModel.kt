package com.example.submission2.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.submission2.data.TvShow
import com.example.submission2.data.source.TheMovieRepository
import javax.inject.Inject

class TvShowViewModel @Inject constructor(private val theMovieRepository: TheMovieRepository) : ViewModel() {
    private var page = 0

    fun getListTvShow(): LiveData<List<TvShow>> {
        page++
        return theMovieRepository.getAllTvShow(page)
    }
}