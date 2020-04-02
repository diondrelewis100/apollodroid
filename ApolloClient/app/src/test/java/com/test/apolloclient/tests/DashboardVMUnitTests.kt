package com.test.apolloclient.tests

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.test.apolloclient.MoviesQuery
import com.test.apolloclient.ui.dashboard.DashboardViewModel
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class DashboardVMUnitTests {

    companion object {
        val ADMIN_TOKEN = "34d7fda63adebdfb3638699a6f649b2e"
    }

    // to mock main looper that handles UI
    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    fun getViewModel(): DashboardViewModel {
        return DashboardViewModel()
    }

    @Test
    fun DashboardVM_isControllerAttached() {
        val mock = MockDashboardController()
        val vm = getViewModel()
        vm.controller = mock
        vm.controller.onReceivedMovies(listOf(MoviesQuery.Movie("", "", "", "", "")))
        assert(mock.isMoviesReceived)
    }

    @Test
    fun DashboardVM_isControllerReceived() {
        val mock = MockDashboardController()
        val vm = getViewModel()
        vm.controller = mock
        vm.controller.onReceivedMovies(listOf(MoviesQuery.Movie("", "", "", "", "")))
        assert(mock.isMoviesReceived)
    }

    @Test
    fun DashboardVM_isControllerEmptyReceived() {
        val mock = MockDashboardController()
        val vm = getViewModel()
        vm.controller = mock
        vm.controller.onReceivedMovies(listOf())
        assert(!mock.isMoviesReceived)
    }

    @Test
    fun DashboardVM_isControllerReceivedFailed() {
        val mock = MockDashboardController()
        val vm = getViewModel()
        vm.controller = mock
        vm.controller.onReceiveMoviesFailed("")
        assert(mock.isMoviesFetchFailed)
    }

    @Test
    fun DashboardVM_isControllerReceivedFailError() {
        val mock = MockDashboardController()
        val vm = getViewModel()
        vm.controller = mock
        vm.controller.onReceiveMoviesFailed("Error message")
        assertEquals("Error message", mock.movieFetchError)
    }

    @Test
    fun DashboardVM_checkFetchMovies() {
        val mock = MockDashboardController()
        val vm = getViewModel()
        vm.controller = mock
        val latch = CountDownLatch(1)
        val httpClient = vm.fetchPopularMovies(ADMIN_TOKEN)
        assertNotNull(httpClient)
        latch.await(15, TimeUnit.SECONDS)
        assert(mock.isMoviesReceived)
        assertNotNull(mock.fetchedMovies)
    }

}