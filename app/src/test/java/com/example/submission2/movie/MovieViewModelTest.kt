package com.example.submission2.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.submission2.data.Movie
import com.example.submission2.data.source.TheMovieRepository
import com.example.submission2.utils.DataDummy
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest {

    private lateinit var movieViewModel: MovieViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var observer: Observer<List<Movie>>

    @Mock
    private lateinit var theMovieRepository: TheMovieRepository

    @Before
    fun setUp() {
        movieViewModel = MovieViewModel(theMovieRepository)
    }

    @Test
    fun getListMovie() {
        val dummyMovie = DataDummy.generateDummyMovies()
        val movie = MutableLiveData<List<Movie>>()
        movie.value = dummyMovie

        `when`(theMovieRepository.getAllMovie(1)).thenReturn(movie)
        val listMovie = movieViewModel.getListMovie()
        verify(theMovieRepository).getAllMovie(1)

        listMovie.observeForever(observer)
        verify(observer).onChanged(dummyMovie)

        assertNotNull(listMovie)
        assertEquals(20, listMovie.value?.size ?: 0)
    }
}