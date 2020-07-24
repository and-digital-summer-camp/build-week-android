package com.and.newton.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.and.newton.common.viewmodel.UserViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.view.*
import org.greenrobot.eventbus.EventBus


@AndroidEntryPoint
class Login : Fragment() {
    companion object {
        const val LOGIN_SUCCESSFUL: String = "LOGIN_SUCCESSFUL"
    }
    private val RC_SIGN_IN = 0
    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        // Inflate the layout for this fragment
        val layout =  inflater.inflate(R.layout.fragment_login, container, false)

        val viewModel: LoginViewModel by viewModels()


        layout.sign_in_button.setOnClickListener {
            viewModel.authenicate()
        }

       viewModel.googleClient.observe(viewLifecycleOwner, Observer {
           val signInIntent: Intent = it.signInIntent
           startActivityForResult(signInIntent, RC_SIGN_IN)
       })




        return layout
    }

    fun updateUI(){
        EventBus.getDefault().post(LoginNavigator)
    }

    fun handleSignInResult(completedTask: Task<GoogleSignInAccount>){
        try{
            val account = completedTask.getResult(ApiException::class.java)
            //gets token for backend
//            val idToken = account.idToken

            updateUI()
        }catch (e: ApiException){
            Log.d("error", e.toString())
        }

    }

    //Results basically returned from the google client as it went to another activity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RC_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    //Hide Top Nav on login page when lifecycle resumes
    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    //Show Top Nav on login page when lifecycle stops
    //Cause it shares 1 activity so need to restart top bar
    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity).supportActionBar?.show()

    }

}