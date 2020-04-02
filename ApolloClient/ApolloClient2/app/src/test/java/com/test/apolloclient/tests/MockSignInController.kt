package com.test.apolloclient.tests

import com.test.apolloclient.ui.main.MainViewModel

class MockSignInController: MainViewModel.SignInController {

    var isSubmitted = false
    var isLoggedIn = false
    var isLoginError = false
    var loginError = ""
    var retrievedToken = ""

    override fun onSubmit() {
        isSubmitted = true
    }

    override fun onLogin(token: String) {
        isLoggedIn = true
        retrievedToken = token
    }

    override fun onFailedLogin(err: String) {
        isLoginError = true
        loginError = err
    }

}