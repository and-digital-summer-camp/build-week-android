package com.and.newton.comms

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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.comms_home_fragment, container, false)


        val viewModel: CommsHomeViewModel by viewModels()

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