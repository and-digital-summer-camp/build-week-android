package com.and.newton.comms

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.*
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.and.newton.common.utils.AppPreferences
import com.and.newton.common.viewmodel.UserViewModel
import androidx.lifecycle.liveData
import com.and.newton.comms.domain.data.Article
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.comms_home_fragment.view.*
import timber.log.Timber

@AndroidEntryPoint
class CommsHomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    private val userViewModel: UserViewModel by activityViewModels()
    private val viewModel: CommsHomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.comms_home_fragment, container, false)

        if(!AppPreferences.isLogged){
            userViewModel.unAuthenticatedUser()
        }

        userViewModel.authenticatedState.observe(viewLifecycleOwner, Observer { authenticatedState ->
            when(authenticatedState){
                UserViewModel.AuthenticationState.AUTHENTICATED -> {

                }
                UserViewModel.AuthenticationState.UNAUTHENTICATED -> {

                    val uri = Uri.parse("App://login")
                    findNavController().navigate(uri)
                }
                else -> {
                    Log.d("login",authenticatedState.toString()  )
                }
            }

        })

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


    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_filter -> {
            Timber.d("onOptionsItemSelected filter selected")
            true
        }
        else ->  super.onOptionsItemSelected(item)
    }

}
