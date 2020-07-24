package com.and.newton.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.view.*
import javax.inject.Inject

@AndroidEntryPoint
class Login : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val layout =  inflater.inflate(R.layout.fragment_login, container, false)

        val viewModel: LoginViewModel by viewModels()

        layout.login_btn.setOnClickListener {
            viewModel.authenicate()
        }
        viewModel.loginState.observe(viewLifecycleOwner, Observer {

           if(it == LoginState.AUTHENTICATED) {
               findNavController().navigate(R.id.action_login_to_nav_comms)
            }
        })
        return layout
    }

}