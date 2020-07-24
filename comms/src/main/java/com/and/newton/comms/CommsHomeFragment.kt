package com.and.newton.comms

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import com.and.newton.comms.domain.data.Article
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class CommsHomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


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


    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_filter -> {
            Timber.d("onOptionsItemSelected filter selected")
            true
        }
        else ->  super.onOptionsItemSelected(item)
    }

}