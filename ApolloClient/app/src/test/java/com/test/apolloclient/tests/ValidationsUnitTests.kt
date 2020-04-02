package com.test.apolloclient.tests

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.test.apolloclient.helpers.Validator
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class ValidationsUnitTests {

    // to mock main looper that handles UI
    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun Validations_CheckBlankEmail() {
        val validator = Validator()
        val result = validator.validateEmail("")
        assertEquals(result, false)
    }

    @Test
    fun Validations_CheckInvalidEmail() {
        val validator = Validator()
        val result = validator.validateEmail("abc")
        assertEquals(result, false)
    }

    @Test
    fun Validations_CheckValidEmail() {
        val validator = Validator()
        val result = validator.validateEmail("abc@abc.com")
        assertEquals(result, true)
    }

}