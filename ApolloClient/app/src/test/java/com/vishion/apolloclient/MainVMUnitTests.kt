package com.vishion.apolloclient

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.test.apolloclient.ui.main.MainViewModel
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

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
        vm.controller.onLogin()
        assert(mock.isLoggedIn)
    }

    @Test
    fun MainVM_isControllerLoggedin() {
        val mock = MockSignInController()
        val vm = getViewModel()
        vm.controller = mock
        vm.controller.onLogin()
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

}