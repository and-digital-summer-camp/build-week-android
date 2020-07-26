package com.and.newton.common.viewmodel

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.and.newton.common.utils.AppPreferences
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.hilt.android.qualifiers.ApplicationContext


//Shared Usermodel
class UserViewModel @ViewModelInject constructor(@ApplicationContext val context: Context) : ViewModel() {
    enum class AuthenticationState {
        AUTHENTICATED,
        UNAUTHENTICATED,
        INVALID_AUTHENTICATION
    }

    var mGoogleSignInClient: GoogleSignInClient


    init {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("594316770168-07dhnt20g4svgtp6vqqua9cucp4moqc2.apps.googleusercontent.com")
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
    }

    private var _authenticatedState = MutableLiveData<AuthenticationState>()
    val authenticatedState: LiveData<AuthenticationState>
        get() = _authenticatedState


    fun signout() {
        AppPreferences.clear()
        mGoogleSignInClient.signOut()
        _authenticatedState.value = AuthenticationState.UNAUTHENTICATED
    }

    fun unAuthenticatedUser() {
        _authenticatedState.value = AuthenticationState.UNAUTHENTICATED
    }

    fun verifyUser(account: GoogleSignInAccount?) {
        AppPreferences.isLogged = true
        _authenticatedState.value = AuthenticationState.AUTHENTICATED
    }


}