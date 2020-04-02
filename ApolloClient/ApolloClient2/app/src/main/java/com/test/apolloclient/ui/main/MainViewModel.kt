package com.test.apolloclient.ui.main

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.test.apolloclient.helpers.HttpHelper
import com.test.apolloclient.helpers.Validator
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class MainViewModel : BaseObservable() {

    // controller interface to inform UI
    interface SignInController {
        fun onSubmit()
        fun onLogin(token: String)
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
        if (!Validator().validateEmail(email)) {
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

        val httpHelper = HttpHelper()
        val httpClient = httpHelper.getHttpClient()

        // val json: = "{\"username\":${email},\"password\":\"${password}\"}"

        val reqBody = JSONObject()
        reqBody.put("username", email)
        reqBody.put("password", password)

        val requestBody = RequestBody.create(
            MediaType.parse("application/json"),
            reqBody.toString()
        )

        val request = Request.Builder()
            .url(httpHelper.AUTH_ENDPOINT)
            .post(requestBody)
            .build()

        val call: Call = httpClient.newCall(request)
        call.enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val strResponse: String? = response.body()?.string()

                val responseJson = JSONObject(strResponse)
                if (response.code() == 200) {
                    controller.onLogin(responseJson.getString("token"))
                } else {
                    val errMsg = responseJson.getString("error")
                    controller.onFailedLogin(errMsg)
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                controller.onFailedLogin("Invalid username or password")
            }
        })
    }
}
