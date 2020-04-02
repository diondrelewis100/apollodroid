package com.test.apolloclient.ui.dashboard

import androidx.databinding.BaseObservable
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.exception.ApolloHttpException
import com.apollographql.apollo.request.RequestHeaders
import com.test.apolloclient.MoviesQuery
import com.test.apolloclient.helpers.HttpHelper
import org.json.JSONObject

class DashboardViewModel : BaseObservable() {

    // controller interface to inform UI
    interface DashboardController {
        fun onReceivedMovies(movies: List<MoviesQuery.Movie?>)
        fun onReceiveMoviesFailed(err: String)
    }

    lateinit var controller: DashboardController

    private var movies: List<MoviesQuery.Movie?>? = null


    // fetch popular movies from apollo client graphql server
    fun fetchPopularMovies(token: String) {

        val httpHelper = HttpHelper()

        val apolloClient = httpHelper.getApolloClientForGraphQL()

        // add authorization header
        val requestHeader = RequestHeaders.builder()
            .addHeader("authorization", token)
            .build()

        apolloClient.query(MoviesQuery()).requestHeaders(requestHeader)
            .enqueue(object: ApolloCall.Callback<MoviesQuery.Data>() {

                // successful response
                override fun onResponse(response: Response<MoviesQuery.Data>) {
                    movies = response.data()?.movies
                    // update UI via controller with fetched movies
                    movies?.let { controller?.onReceivedMovies(it) }
                }

                // failure response
                override fun onFailure(e: ApolloException) {

                    try {
                        val strResponse = (e as ApolloHttpException).rawResponse()?.body()?.string()
                        val jsonResponse = JSONObject(strResponse)
                        val errMessage = jsonResponse.getJSONArray("errors").getJSONObject(0).getString("message")

                        // update UI via controller about error
                        controller?.onReceiveMoviesFailed(errMessage)
                    } catch (eParse: Exception) {

                        eParse.printStackTrace()

                        // update UI via controller about error
                        controller?.onReceiveMoviesFailed(e.message!!)
                    }
                }
            })

    }

}
