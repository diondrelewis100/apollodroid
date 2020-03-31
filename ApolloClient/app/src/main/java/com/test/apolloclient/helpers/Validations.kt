package com.test.apolloclient.helpers

class Validations {
    companion object {
        fun validateEmail(email: String): Boolean {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }
}