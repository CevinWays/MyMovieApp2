package com.example.submission2.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.submission2.data.TvShow
import com.example.submission2.data.source.TheMovieRepository
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
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class TvShowViewModelTest {
    private lateinit var tvShowViewModel: TvShowViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var observer: Observer<List<TvShow>>

    @Mock
    private lateinit var theMovieRepository: TheMovieRepository

    @Before
    fun setUp() {
        tvShowViewModel = TvShowViewModel(theMovieRepository)
    }

    @Test
    fun getListTvShow() {
        val dummyTv = DataDummy.generateDummyTvShow()
        val tvShow = MutableLiveData<List<TvShow>>()
        tvShow.value = dummyTv

        Mockito.`when`(theMovieRepository.getAllTvShow(1)).thenReturn(tvShow)
        val listTv = tvShowViewModel.getListTvShow()
        verify(theMovieRepository).getAllTvShow(1)

        listTv.observeForever(observer)
        verify(observer).onChanged(dummyTv)

        assertNotNull(listTv)
        assertEquals(20, listTv.value?.size ?: 0)
    }
}