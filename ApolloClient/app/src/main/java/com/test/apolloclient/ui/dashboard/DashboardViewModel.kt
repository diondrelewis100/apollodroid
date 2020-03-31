package com.test.apolloclient.ui.dashboard

import android.app.Activity
import android.util.Log
import androidx.databinding.BaseObservable
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.test.apolloclient.BuildConfig
import com.test.apolloclient.MoviesQuery
import com.vishion.app.ui.productresult.MovieResultListAdapter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor


class DashboardViewModel : BaseObservable() {

    // controller interface to inform UI
    interface DashboardController {
        fun onReceivedMovies(movies: List<MoviesQuery.Movie?>)
        fun onReceiveMoviesFailed(err: String)
    }

    lateinit var controller: DashboardController

    private var movies: List<MoviesQuery.Movie?>? = null


    // get logging interceptor for okhttp3, helpful to log request/response and debug graphql calls
    fun getLogginInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return interceptor
    }

    // configure get http client to be used by apollo android client to make http calls to graphql server
    fun getHttpClient(): OkHttpClient {
        val interceptor = getLogginInterceptor()

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
        return okHttpClient
    }

    // configure and get apollo client
    fun getApolloClient(): ApolloClient {
        val okHttpClient = getHttpClient()
        val apolloClient = ApolloClient.builder()
            .serverUrl("http://34.205.20.152:4000/graphql")
            .okHttpClient(okHttpClient)
            .build()
        return apolloClient
    }

    // fetch popular movies from apollo client graphql server
    fun fetchPopularMovies() {

        val apolloClient = getApolloClient()

        apolloClient.query(MoviesQuery())
            .enqueue(object: ApolloCall.Callback<MoviesQuery.Data>() {

                // successful response
                override fun onResponse(response: Response<MoviesQuery.Data>) {
                    Log.d("apolloClient", response.data()?.movies.toString())
                    movies = response.data()?.movies
                    // update UI via controller with fetched movies
                    movies?.let { controller?.onReceivedMovies(it) }
                }

                // failure response
                override fun onFailure(e: ApolloException) {
                    Log.e("apolloClient", e.message, e)
                    // update UI via controller about error
                    controller?.onReceiveMoviesFailed(e.message!!)
                }
            })

    }

}
