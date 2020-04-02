package com.test.apolloclient.helpers

import com.apollographql.apollo.ApolloClient
import com.test.apolloclient.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class HttpHelper {

    val AUTH_ENDPOINT = "http://34.205.20.152:3000/login"
    val GRAPHQL_ENDPOINT = "http://34.205.20.152:4000/graphql"

    // get logging interceptor for okhttp3, helpful to log request/response and debug graphql calls
    fun getLogginInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return interceptor
    }

    // configure get http client to be used by apollo android client to make http calls to graphql server
    fun getHttpClient(): OkHttpClient {
        val interceptor = getLogginInterceptor()

        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    // configure and get apollo client
    fun getApolloClientForGraphQL(): ApolloClient {
        val okHttpClient = getHttpClient()

        return ApolloClient.builder()
            .serverUrl(GRAPHQL_ENDPOINT)
            .okHttpClient(okHttpClient)
            .build()
    }

}