package com.and.newton.common.viewmodel

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.and.newton.common.domain.UserRepo
import com.and.newton.common.domain.data.GoogleUserToken
import com.and.newton.common.utils.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import timber.log.Timber


//Shared Usermodel
class UserViewModel @ViewModelInject constructor(
    private val userRepo: UserRepo,
    @ApplicationContext var context: Context
) : ViewModel() {
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


    fun authenticatedUser() {
        _authenticatedState.value = AuthenticationState.AUTHENTICATED
    }

    fun verifyUser(account: GoogleSignInAccount?) {
        val googleToken:String = account?.idToken?:""

        MainScope().launch {
            if (googleToken != null) {
                val result = userRepo.getUser(GoogleUserToken(googleToken))
                if (result != null) {
                    if (result.token != null) {
                        AppPreferences.isLogged = true
                        AppPreferences.token = result.token
                        AppPreferences.access_level = getUserRoleFromToken(result.token)
                        AppPreferences.first_name = account?.displayName?:account?.givenName?:"Guest"
                        AppPreferences.last_name = account?.familyName?:"User"
                        AppPreferences.email = account?.email?:""
                        _authenticatedState.value = AuthenticationState.AUTHENTICATED
                    } else {
                        _authenticatedState.value = AuthenticationState.INVALID_AUTHENTICATION
                    }
                } else {
                    _authenticatedState.value = AuthenticationState.UNAUTHENTICATED
                }
            }
        }
    }

    private fun getUserRoleFromToken(token:String):String {
        val t: JWTToken<JWTAuthPayload>? = JWTUtils.decode(token, JWTDecoder, Base64DecoderImpl)
        Timber.d("decodeToken :: ${t?.payload?.role}")
        Timber.d("decodeToken :: ${t?.payload?.expriryTime}")
        return t?.payload?.role?:"user"
    }

}