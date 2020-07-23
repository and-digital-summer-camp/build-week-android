package com.and.newton.login

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel @ViewModelInject constructor(): ViewModel() {

    private var _loginState = MutableLiveData<LoginState>()
    val loginState: LiveData<LoginState>
        get() = _loginState


    fun authenicate() {
        _loginState.value = LoginState.AUTHENTICATED
    }

}