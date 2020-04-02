package com.test.apolloclient.ui.dashboard

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.apolloclient.DashboardActivity
import com.test.apolloclient.MoviesQuery
import com.test.apolloclient.R
import com.test.apolloclient.databinding.DashboardFragmentBinding
import com.test.app.ui.productresult.MovieResultListAdapter
import com.test.app.ui.productresult.OnListFragmentInteractionListener
import kotlinx.android.synthetic.main.dashboard_fragment.*


class DashboardFragment : Fragment(), DashboardViewModel.DashboardController,
    OnListFragmentInteractionListener {

    companion object {
        // the fragment instantiator
        fun newInstance() = DashboardFragment()
    }

    private lateinit var viewModel: DashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // inflat layout DataBindingUtil
        val binding: DashboardFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.dashboard_fragment, container, false)
        viewModel = DashboardViewModel()
        // assign this fragment as controller to handle UI actions from view model
        viewModel.controller = this
        binding.dashboardModel = viewModel
        // required to specify current fragment as lifecycle owner
        binding.lifecycleOwner = this
        // provide root view from data binding
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // setting linear single column list layout
        lstMovies.layoutManager = LinearLayoutManager(activity)

        // get user token from activity
        val token = (activity as DashboardActivity).token

        // fetch popular movies from apollo graphql server
        viewModel.fetchPopularMovies(token)
    }

    override fun onReceivedMovies(movies: List<MoviesQuery.Movie?>) {
        activity?.runOnUiThread {
            // create and assign adapter to recycler view
            val adapter = MovieResultListAdapter(movies, this)

            if (lstMovies != null)
                lstMovies.adapter = adapter
        }
    }

    override fun onReceiveMoviesFailed(err: String) {
        activity?.runOnUiThread {
            // show alert dialog
            AlertDialog
            .Builder(activity!!)
            .setCancelable(false)
            .setMessage(err)
            .setNegativeButton("OK", null)
            .create()
            .show()
        }
    }

    override fun onListFragmentInteraction(item: MoviesQuery.Movie?) {
        // movie item is optional value. Verify not null and show movie review page in native browser
        item?.let {
            val id = it.id
            val browserIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse("https://www.themoviedb.org/movie/$id?language=en-US"))
            startActivity(browserIntent)
        }
    }

}
