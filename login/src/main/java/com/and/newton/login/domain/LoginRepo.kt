package com.and.newton.login.domain

import com.google.android.gms.auth.api.signin.GoogleSignInClient

interface LoginRepo {
    fun googleAuthenication() : GoogleSignInClient
}