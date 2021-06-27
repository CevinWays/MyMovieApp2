package com.example.submission2

import android.app.Application
import com.example.submission2.di.DaggerMovieComponent

class App : Application() {
    val movieComponent = DaggerMovieComponent.create()
}