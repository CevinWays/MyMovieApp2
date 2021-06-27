package com.example.submission2.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.submission2.data.source.TheMovieDataSource
import com.example.submission2.data.source.remote.RemoteDataSource
import com.example.submission2.data.source.remote.response.MovieDetailResponse
import com.example.submission2.data.source.remote.response.MovieResponse
import com.example.submission2.data.source.remote.response.TvDetailResponse
import com.example.submission2.data.source.remote.response.TvResponse
import com.example.submission2.utils.DataDummy
import com.example.submission2.utils.LiveDataTestUtil
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.*
import retrofit2.Call
import retrofit2.Response

@RunWith(MockitoJUnitRunner.Silent::class)
class TheMovieRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val theMovieRepository = FakeTheMovieRepository(remote)
    private val idMovie = 460465
    private val idTv = 120168

    private val movieResponse = DataDummy.generateMovieResponse()
    private val tvShowResponse = DataDummy.generateTvResponse()
    private val detailMovieResponse = DataDummy.generateMovieDetailResponse()
    private val detailTvResponse = DataDummy.generateTvDetailResponse()

    private val apiMovieResponse = Response.success(movieResponse)
    private val apiTvResponse = Response.success(tvShowResponse)
    private val apiDetailMovieResponse = Response.success(detailMovieResponse)
    private val apiDetailTvResponse = Response.success(detailTvResponse)

    private val mockCallMovie = mock<Call<MovieResponse>> {
        on { execute() } doReturn apiMovieResponse
    }

    private val mockCallTv = mock<Call<TvResponse>> {
        on { execute() } doReturn apiTvResponse
    }

    private val mockCallDetailMovie = mock<Call<MovieDetailResponse>> {
        on { execute() } doReturn apiDetailMovieResponse
    }

    private val mockCallDetailTv = mock<Call<TvDetailResponse>> {
        on { execute() } doReturn apiDetailTvResponse
    }

    @Test
    fun getAllMovie() {
        doAnswer {
            (it.arguments[0] as TheMovieDataSource.LoadMoviesCallback).onResponse(mockCallMovie, apiMovieResponse)
            null
        }.`when`(remote).getAll(any<TheMovieDataSource.LoadMoviesCallback>(), eq(1))
        val movie = LiveDataTestUtil.getValue(theMovieRepository.getAllMovie(1))
        verify(remote).getAll(any<TheMovieDataSource.LoadMoviesCallback>(), eq(1))
        assertNotNull(movie)
        assertEquals(movieResponse.results.size.toLong(), movie.size.toLong())
    }

    @Test
    fun getAllTvShow() {
        doAnswer {
            (it.arguments[0] as TheMovieDataSource.LoadTvCallback).onResponse(mockCallTv, apiTvResponse)
            null
        }.`when`(remote).getAll(any<TheMovieDataSource.LoadTvCallback>(), eq(1))
        val tvShow = LiveDataTestUtil.getValue(theMovieRepository.getAllTvShow(1))
        verify(remote).getAll(any<TheMovieDataSource.LoadTvCallback>(), eq(1))
        assertNotNull(tvShow)
        assertEquals(tvShowResponse.results.size.toLong(), tvShow.size.toLong())
    }

    @Test
    fun getDetailMovie() {
        doAnswer {
            (it.arguments[0] as TheMovieDataSource.LoadDetailMovieCallback).onResponse(
                mockCallDetailMovie,
                apiDetailMovieResponse
            )
            null
        }.`when`(remote).getDetail(any<TheMovieDataSource.LoadDetailMovieCallback>(), eq(idMovie))
        val movie = LiveDataTestUtil.getValue(theMovieRepository.getDetailMovie(idMovie))
        verify(remote).getDetail(any<TheMovieDataSource.LoadDetailMovieCallback>(), eq(idMovie))
        assertNotNull(movie)
        assertEquals(detailMovieResponse.id, movie.id)
        assertEquals(detailMovieResponse.title, movie.title)
        assertEquals(detailMovieResponse.overview, movie.overview)
        assertEquals(detailMovieResponse.voteAverage ?: 0.0, movie.voteAverage ?: 0.0, 0.0001)
        assertEquals(detailMovieResponse.releaseDate, movie.releaseDate)
        assertEquals(detailMovieResponse.posterPath, movie.posterPath)
    }

    @Test
    fun getDetailTvShow() {
        doAnswer {
            (it.arguments[0] as TheMovieDataSource.LoadDetailTvCallback).onResponse(mockCallDetailTv, apiDetailTvResponse)
            null
        }.`when`(remote).getDetail(any<TheMovieDataSource.LoadDetailTvCallback>(), eq(idTv))
        val tvShow = LiveDataTestUtil.getValue(theMovieRepository.getDetailTv(idTv))
        verify(remote).getDetail(any<TheMovieDataSource.LoadDetailTvCallback>(), eq(idTv))
        assertNotNull(tvShow)
        assertEquals(detailTvResponse.id, tvShow.id)
        assertEquals(detailTvResponse.name, tvShow.name)
        assertEquals(detailTvResponse.overview, tvShow.overview)
        assertEquals(detailTvResponse.voteAverage ?: 0.0, tvShow.voteAverage ?: 0.0, 0.0001)
        assertEquals(detailTvResponse.firstAirDate, tvShow.firstAirDate)
        assertEquals(detailTvResponse.posterPath, tvShow.posterPath)
    }
}