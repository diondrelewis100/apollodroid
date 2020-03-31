package com.vishion.apolloclient

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.test.apolloclient.MoviesQuery
import com.test.apolloclient.ui.dashboard.DashboardViewModel
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class DashboardVMUnitTests {

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

}