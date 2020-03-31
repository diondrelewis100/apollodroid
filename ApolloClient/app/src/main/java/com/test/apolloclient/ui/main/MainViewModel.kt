package com.test.apolloclient.ui.main

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.test.apolloclient.helpers.Validations

class MainViewModel : BaseObservable() {

    // controller interface to inform UI
    interface SignInController {
        fun onSubmit()
        fun onLogin()
        fun onFailedLogin(err: String)
    }

    lateinit var controller: SignInController

    // mutable bounded live data to email validation error at runtime
    private var _emailError = MutableLiveData<Boolean>()
    val emailError: LiveData<Boolean> = _emailError

    // mutable live data to password validation error at runtime
    private var _passwordError = MutableLiveData<Boolean>()
    val passwordError: LiveData<Boolean> = _passwordError

    // bindable property bound with email field, as it is two way bounded,
    // all values user types in EditText automatically updated in this property
    var email: String = ""
    @Bindable
    get

    set(value) {
        if (field != value)
            field = value
    }

    // bindable property bound with password field, as it is two way bounded,
    // all values user types in EditText automatically updated in this property
    var password: String = ""
    @Bindable
    get

    set(value) {
        if (field != value)
            field = value
    }

    init {
        // initialize errors properties with clean state
        _emailError.value = false
        _passwordError.value = false
    }

    // method called when user clicks sign in button
    // business logic to validate email and password and performs login
    fun btnSubmitPressed() {
        if (!Validations.validateEmail(email)) {
            _emailError.value = true
        } else if (password.isEmpty()) {
            _emailError.value = false
            _passwordError.value = true
        } else {
            _emailError.value = false
            _passwordError.value = false
            controller.onSubmit()
            doLogin()
        }
    }

    // performs login with dummy inline data
    fun doLogin() {
        // TODO: auth can also be done with ApolloServer
        if (email == "diondre@ibgtraining.com" && password == "123456") {
            // updates UI via controller if login is successful
            controller.onLogin()
        } else {
            // updates UI via controller if login is failed
            controller.onFailedLogin("Invalid username or password")
        }
    }
}
