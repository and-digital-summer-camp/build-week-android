package com.and.newton.login.domain

import android.content.Context
import com.and.newton.login.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class LoginRepoImpl @Inject constructor(
    @ApplicationContext val context: Context
) :LoginRepo {

    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun googleAuthenication(): GoogleSignInClient {

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("594316770168-07dhnt20g4svgtp6vqqua9cucp4moqc2.apps.googleusercontent.com")
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(context, gso);

         return mGoogleSignInClient
    }


}