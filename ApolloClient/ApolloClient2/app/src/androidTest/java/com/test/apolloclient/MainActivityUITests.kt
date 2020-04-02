package com.test.apolloclient

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.test.apolloclient.MainActivity
import com.test.apolloclient.R
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch

@RunWith(AndroidJUnit4::class)
class MainActivityUITests {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun MainActivity_CheckLogin() {
        onView(withId(R.id.edtEmail)).perform(typeText("diondre@ibgtraining.com"))
        onView(withId(R.id.edtPassword)).perform(typeText("123456"))
        onView(withId(R.id.btnSignIn)).perform(click())
    }


    @Test
    fun MainActivity_CheckDashboard() {
        onView(withId(R.id.edtEmail)).perform(typeText("diondre@ibgtraining.com"))
        onView(withId(R.id.edtPassword)).perform(typeText("123456"))
        onView(withId(R.id.btnSignIn)).perform(click())
    }

}
