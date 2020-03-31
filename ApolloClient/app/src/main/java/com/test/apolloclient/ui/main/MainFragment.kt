package com.test.apolloclient.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.test.apolloclient.DashboardActivity
import com.test.apolloclient.R
import com.test.apolloclient.databinding.MainFragmentBinding

class MainFragment : Fragment(), MainViewModel.SignInController {

    companion object {
        // the fragment instantiator
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // inflat layout DataBindingUtil
        val binding: MainFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        viewModel = MainViewModel()
        // assign this fragment as controller to handle UI actions from view model
        viewModel.controller = this
        binding.signinModel = viewModel
        // required to specify current fragment as lifecycle owner
        binding.lifecycleOwner = this
        // provide root view from data binding
        return binding.root
    }

    override fun onSubmit() {}

    override fun onLogin() {
        // login successful, show dashboard screen and clear the stack
        val intent = Intent(activity, DashboardActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        activity?.startActivity(intent)
    }

    override fun onFailedLogin(err: String) {
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
