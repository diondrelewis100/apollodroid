package com.test.apolloclient

import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.test.apolloclient.databinding.DashboardActivityBinding
import com.test.apolloclient.ui.dashboard.DashboardFragment


class DashboardActivity : AppCompatActivity() {

    var token: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        token = intent.extras.getString("token")

        // to allow app to make plain http calls instead of https
        if (Build.VERSION.SDK_INT > 9) {
            val policy =
                StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }
        DataBindingUtil.setContentView<DashboardActivityBinding>(this, R.layout.dashboard_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, DashboardFragment.newInstance())
                .commitNow()
        }
    }

}
