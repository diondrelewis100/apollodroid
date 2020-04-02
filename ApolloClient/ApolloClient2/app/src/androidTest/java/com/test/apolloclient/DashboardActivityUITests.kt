package com.test.apolloclient

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.test.apolloclient.DashboardActivity
import com.test.apolloclient.R
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class DashboardActivityUITests {

    @get:Rule
    val mActivityTestRule: ActivityTestRule<DashboardActivity> =
        object : ActivityTestRule<DashboardActivity>(DashboardActivity::class.java) {
            override fun getActivityIntent(): Intent {
                val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
                return Intent(targetContext, DashboardActivity::class.java).apply {
                    putExtra("token", "34d7fda63adebdfb3638699a6f649b2e")
                }
            }
        }

    @Test
    fun DashboardActivity_CheckList() {
        // it will take time to load data, wait for 15 seconds
        val latch = CountDownLatch(1)
        latch.await(15, TimeUnit.SECONDS)
        onView(withId(R.id.lstMovies)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
    }


}
