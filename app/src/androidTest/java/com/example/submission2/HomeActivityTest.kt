package com.example.submission2

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.submission2.home.HomeActivity
import com.example.submission2.utils.DataDummy
import com.example.submission2.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeActivityTest {

    private val listMovie = DataDummy.generateMovieResponse().results
    private val listTvShow = DataDummy.generateTvResponse().results

    @get:Rule
    var activityRule = ActivityScenarioRule(HomeActivity::class.java)

    @Before
    fun setUp() {
        ActivityScenario.launch(HomeActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun loadMovie() {
        onView(withId(R.id.rvMovie))
            .check(matches(isDisplayed()))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(listMovie.size))
    }

    @Test
    fun openDetailMovie() {
        onView(withId(R.id.rvMovie)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
            click()
        ))
        onView(withId(R.id.textViewTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewReleaseDate)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewOverview)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewRating)).check(matches(isDisplayed()))
        onView(withId(R.id.imageBackDrop)).check(matches(isDisplayed()))
    }

    @Test
    fun loadTvShow() {
        onView(withText("Tv Show")).perform(click())
        onView(withId(R.id.rvTvShow))
            .check(matches(isDisplayed()))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(listTvShow.size))
    }

    @Test
    fun openDetailTvShow() {
        onView(withText("Tv Show")).perform(click())
        onView(withId(R.id.rvTvShow)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
            click()
        ))
        onView(withId(R.id.textViewTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewReleaseDate)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewOverview)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewRating)).check(matches(isDisplayed()))
        onView(withId(R.id.imageBackDrop)).check(matches(isDisplayed()))
    }
}