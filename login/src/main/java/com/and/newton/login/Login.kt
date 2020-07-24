package com.and.newton.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.view.*
import org.greenrobot.eventbus.EventBus
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

        layout.sign_in_button.setOnClickListener {
            viewModel.authenicate()
        }

        viewModel.loginState.observe(viewLifecycleOwner, Observer {
           if(it == LoginState.AUTHENTICATED) {
               EventBus.getDefault().post(LoginNavigator)
            }
        })

        return layout
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.hide()

    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity).supportActionBar?.show()

    }

}