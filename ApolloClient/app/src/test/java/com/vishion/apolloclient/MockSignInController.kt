package com.vishion.apolloclient

import com.test.apolloclient.ui.main.MainViewModel

class MockSignInController: MainViewModel.SignInController {

    var isSubmitted = false
    var isLoggedIn = false
    var isLoginError = false
    var loginError = ""

    override fun onSubmit() {
        isSubmitted = true
    }

    override fun onLogin() {
        isLoggedIn = true
    }

    override fun onFailedLogin(err: String) {
        isLoginError = true
        loginError = err
    }

}