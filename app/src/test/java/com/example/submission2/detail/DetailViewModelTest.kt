package com.example.submission2.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.submission2.data.source.TheMovieRepository
import com.example.submission2.data.source.remote.response.MovieDetailResponse
import com.example.submission2.data.source.remote.response.TvDetailResponse
import com.example.submission2.utils.DataDummy
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {
    @Mock
    private lateinit var theMovieRepository: TheMovieRepository
    private lateinit var detailViewModel: DetailViewModel
    private val dummyDetailMovie = DataDummy.generateMovieDetailResponse()
    private val dummyDetailTv = DataDummy.generateTvDetailResponse()
    private val idMovie = 460465
    private val idTv = 120168

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var observerMovie: Observer<MovieDetailResponse>

    @Mock
    private lateinit var observerTv: Observer<TvDetailResponse>

    @Before
    fun setUp() {
        detailViewModel = DetailViewModel(theMovieRepository)
    }

    @Test
    fun getMovie() {
        val dummyMovie = DataDummy.generateMovieDetailResponse()
        val movie = MutableLiveData<MovieDetailResponse>()
        movie.value = dummyMovie

        Mockito.`when`(theMovieRepository.getDetailMovie(idMovie)).thenReturn(movie)
        val movieDetail = detailViewModel.getMovie(idMovie)
        Mockito.verify(theMovieRepository).getDetailMovie(idMovie)

        movie.observeForever(observerMovie)
        Mockito.verify(observerMovie).onChanged(dummyMovie)

        assertNotNull(movieDetail)
        assertEquals(dummyDetailMovie.id, movieDetail.value?.id)
        assertEquals(dummyDetailMovie.title, movieDetail.value?.title)
        assertEquals(dummyDetailMovie.overview, movieDetail.value?.overview)
        assertEquals(dummyDetailMovie.voteAverage ?: 0.0, movieDetail.value?.voteAverage ?: 0.0, 0.0001)
        assertEquals(dummyDetailMovie.releaseDate, movieDetail.value?.releaseDate)
        assertEquals(dummyDetailMovie.posterPath, movieDetail.value?.posterPath)
    }

    @Test
    fun getTvShow() {
        val dummyTv = DataDummy.generateTvDetailResponse()
        val tv = MutableLiveData<TvDetailResponse>()
        tv.value = dummyTv

        Mockito.`when`(theMovieRepository.getDetailTv(idTv)).thenReturn(tv)
        val tvDetail = detailViewModel.getTvShow(idTv)
        Mockito.verify(theMovieRepository).getDetailTv(idTv)

        tv.observeForever(observerTv)
        Mockito.verify(observerTv).onChanged(dummyTv)

        assertNotNull(tvDetail)
        assertEquals(dummyDetailTv.id, tvDetail.value?.id)
        assertEquals(dummyDetailTv.name, tvDetail.value?.name)
        assertEquals(dummyDetailTv.overview, tvDetail.value?.overview)
        assertEquals(dummyDetailTv.voteAverage ?: 0.0, tvDetail.value?.voteAverage ?: 0.0, 0.0001)
        assertEquals(dummyDetailTv.firstAirDate, tvDetail.value?.firstAirDate)
        assertEquals(dummyDetailTv.posterPath, tvDetail.value?.posterPath)
    }
}