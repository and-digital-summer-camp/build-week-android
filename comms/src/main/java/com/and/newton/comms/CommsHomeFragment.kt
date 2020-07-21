package com.and.newton.comms

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class CommsHomeFragment : Fragment() {

    companion object {
        fun newInstance() = CommsHomeFragment()
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.comms_home_fragment, container, false)

        Timber.d("Fragment :: onCreateView")

         val viewModel: CommsHomeViewModel by viewModels()

            viewModel.recipe.observe(viewLifecycleOwner, Observer { t ->
                Timber.d("Response::"+t)
            })

        return layout
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        // TODO: Use the ViewModel
//    }

}