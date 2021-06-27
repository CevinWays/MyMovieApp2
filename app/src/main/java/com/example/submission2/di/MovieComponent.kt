package com.example.submission2.di

import com.example.submission2.detail.DetailActivity
import com.example.submission2.movie.MovieFragment
import com.example.submission2.tvshow.TvShowFragment
import com.example.submission2.utils.retrofit.ApiConfig
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiConfig::class])
interface MovieComponent {
    fun inject(fragment: MovieFragment)
    fun inject(fragment: TvShowFragment)
    fun inject(activity: DetailActivity)
}