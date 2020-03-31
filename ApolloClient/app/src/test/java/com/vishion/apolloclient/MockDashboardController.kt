package com.vishion.apolloclient

import com.test.apolloclient.MoviesQuery
import com.test.apolloclient.ui.dashboard.DashboardViewModel

class MockDashboardController: DashboardViewModel.DashboardController {

    var isMoviesReceived = false
    var isMoviesFetchFailed = false
    var movieFetchError = ""

    override fun onReceivedMovies(movies: List<MoviesQuery.Movie?>) {
        if (movies.isNotEmpty()) {
            isMoviesReceived = true
        }
    }

    override fun onReceiveMoviesFailed(err: String) {
        isMoviesFetchFailed = true
        movieFetchError = err
    }

}