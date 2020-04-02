package com.test.apolloclient.tests

import com.test.apolloclient.MoviesQuery
import com.test.apolloclient.ui.dashboard.DashboardViewModel

class MockDashboardController: DashboardViewModel.DashboardController {

    var isMoviesReceived = false
    var isMoviesFetchFailed = false
    var movieFetchError = ""
    var fetchedMovies: List<MoviesQuery.Movie?>? = null

    override fun onReceivedMovies(movies: List<MoviesQuery.Movie?>) {
        fetchedMovies = movies
        if (movies.isNotEmpty()) {
            isMoviesReceived = true
        }
    }

    override fun onReceiveMoviesFailed(err: String) {
        isMoviesFetchFailed = true
        movieFetchError = err
    }

}