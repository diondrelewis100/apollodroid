package com.test.apolloclient.tests

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.test.apolloclient.helpers.HttpHelper
import junit.framework.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class HttpHelperUnitTests {

    // to mock main looper that handles UI
    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun DashboardVM_checkLoggingInterceptor() {
        val httpHelper = HttpHelper()
        val interceptor = httpHelper.getLogginInterceptor()
        assertNotNull(interceptor)
    }

    @Test
    fun DashboardVM_checkHttpClient() {
        val httpHelper = HttpHelper()
        val httpClient = httpHelper.getHttpClient()
        assertNotNull(httpClient)
    }

    @Test
    fun DashboardVM_checkApolloClient() {
        val httpHelper = HttpHelper()
        val httpClient = httpHelper.getApolloClientForGraphQL()
        assertNotNull(httpClient)
    }

}