package com.and.newton.login

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.and.newton.login.domain.LoginRepo
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.hilt.android.qualifiers.ApplicationContext

class LoginViewModel @ViewModelInject constructor(private val loginRepo: LoginRepo): ViewModel() {


//    private var _loginState = MutableLiveData<LoginState>()
//    val loginState: LiveData<LoginState>
//        get() = _loginState

    private var _googleClient = MutableLiveData<GoogleSignInClient>()
    val googleClient: LiveData<GoogleSignInClient>
        get() = _googleClient





    fun authenicate() {
        _googleClient.value = loginRepo.googleAuthenication()
//        _loginState.value = LoginState.AUTHENTICATED
    }

}