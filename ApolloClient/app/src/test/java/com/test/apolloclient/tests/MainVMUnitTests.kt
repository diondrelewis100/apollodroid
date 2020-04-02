package com.test.apolloclient.tests

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.test.apolloclient.ui.main.MainViewModel
import junit.framework.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class MainVMUnitTests {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    fun getViewModel(): MainViewModel {
        return MainViewModel()
    }

    @Test
    fun MainVM_isControllerAttached() {
        val mock = MockSignInController()
        val vm = getViewModel()
        vm.controller = mock
        vm.controller.onLogin("")
        assert(mock.isLoggedIn)
    }

    @Test
    fun MainVM_isControllerLoggedin() {
        val mock = MockSignInController()
        val vm = getViewModel()
        vm.controller = mock
        vm.controller.onLogin("")
        assert(mock.isLoggedIn)
    }

    @Test
    fun MainVM_isControllerSubmitted() {
        val mock = MockSignInController()
        val vm = getViewModel()
        vm.controller = mock
        vm.controller.onSubmit()
        assert(mock.isSubmitted)
    }

    @Test
    fun MainVM_isControllerLoginError() {
        val mock = MockSignInController()
        val vm = getViewModel()
        vm.controller = mock
        vm.controller.onFailedLogin("Error")
        assert(mock.isLoginError)
    }

    @Test
    fun MainVM_isControllerLoginErrorMessageCheck() {
        val mock = MockSignInController()
        val vm = getViewModel()
        vm.controller = mock
        vm.controller.onFailedLogin("Error Message")
        assertEquals("Error Message", mock.loginError)
    }

    @Test
    fun MainVM_btnSubmitPressed() {
        val mock = MockSignInController()
        val vm = getViewModel()
        vm.controller = mock

        // check email validation
        vm.email = "abc"
        vm.btnSubmitPressed()
        assertNotNull(vm.emailError.value)

        // check password validation
        vm.email = "diondre@ibgtraining.com"
        vm.btnSubmitPressed()
        assertFalse(vm.emailError.value!!)

        vm.password = ""
        vm.btnSubmitPressed()
        assertNotNull(vm.passwordError.value)

        // check invalid login
        vm.email = "diondre@ibgtraining.com"
        vm.btnSubmitPressed()
        assertFalse(vm.emailError.value!!)

        vm.password = "1234567"
        val latch = CountDownLatch(1)
        vm.btnSubmitPressed()
        latch.await(10, TimeUnit.SECONDS)
        assertFalse(vm.passwordError.value!!)
        assert(mock.isSubmitted)
        assert(mock.isLoginError)

        // check valid login
        vm.email = "diondre@ibgtraining.com"
        vm.btnSubmitPressed()
        assertFalse(vm.emailError.value!!)

        vm.password = "123456"
        vm.btnSubmitPressed()
        latch.await(10, TimeUnit.SECONDS)
        assertFalse(vm.passwordError.value!!)
        assert(mock.isSubmitted)
        assert(mock.isLoggedIn)
    }

}