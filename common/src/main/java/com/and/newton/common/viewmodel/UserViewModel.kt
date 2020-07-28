package com.and.newton.common.viewmodel

import android.content.Context
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.and.newton.common.utils.AppPreferences
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.hilt.android.qualifiers.ApplicationContext


//Shared Usermodel
class UserViewModel @ViewModelInject constructor(@ApplicationContext var context: Context) : ViewModel() {



    enum class AuthenticationState {
        AUTHENTICATED,
        UNAUTHENTICATED,
        INVALID_AUTHENTICATION
    }

    var mGoogleSignInClient: GoogleSignInClient


    init {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("720340655248-ql1qmrgsuj9267ch3lgar94r2dccqm3r.apps.googleusercontent.com")
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
    }

    private var _authenticatedState = MutableLiveData<AuthenticationState>()
    val authenticatedState: LiveData<AuthenticationState>
        get() = _authenticatedState


    fun signout() {
        AppPreferences.clear()
        _authenticatedState.value = AuthenticationState.UNAUTHENTICATED
        mGoogleSignInClient.signOut()
    }

    fun unAuthenticatedUser() {
        _authenticatedState.value = AuthenticationState.UNAUTHENTICATED
    }

    fun authenticatedUser() {
        _authenticatedState.value = AuthenticationState.AUTHENTICATED
    }

    fun verifyUser(account: GoogleSignInAccount?) {
        AppPreferences.isLogged = true
        _authenticatedState.value = AuthenticationState.AUTHENTICATED
    }


}