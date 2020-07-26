package com.and.newton.comms

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.and.newton.common.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.comms_home_fragment.view.*
import org.greenrobot.eventbus.EventBus
import timber.log.Timber

@AndroidEntryPoint
class CommsHomeFragment : Fragment() {


    private val userViewModel: UserViewModel by activityViewModels()
    private val viewModel: CommsHomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.comms_home_fragment, container, false)


        userViewModel.authenticatedState.observe(viewLifecycleOwner, Observer { authenticatedState ->
            when(authenticatedState){
                UserViewModel.AuthenticationState.AUTHENTICATED -> {
                    Log.d("login",authenticatedState.toString() )
                }
                UserViewModel.AuthenticationState.UNAUTHENTICATED -> {
                    Log.d("login",authenticatedState.toString() )
                    val uri = Uri.parse("myApp://login")
                    findNavController().navigate(uri)
                }

                else -> {
                    Log.d("login",authenticatedState.toString()  )
                }
            }

        })
        layout.button.setOnClickListener {
            userViewModel.signout()
        }
        viewModel.articles.observe(viewLifecycleOwner, Observer { t ->
            Timber.d("Mock API Articles List Response::${t}")
        })

        viewModel.user.observe(viewLifecycleOwner, Observer { t ->
            Timber.d("Mock API fragment User Response::${t}")
        })

        viewModel.article.observe(viewLifecycleOwner, Observer { t ->
            Timber.d("Mock API Article id:1 Response::${t}")
        })

        return layout
    }


}