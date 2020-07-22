package com.and.newton.comms.landing_page

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.and.newton.comms.CommsSharedViewModel
import com.and.newton.comms.R
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class CommsLandingPageFragment : Fragment() {

    @Inject
    lateinit var highlightedArticlesAdapter: ArticlesAdapter

    @Inject
    lateinit var otherArticlesAdapter: ArticlesAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.comms_landing_page_fragment, container, false)


        val viewModel: CommsSharedViewModel by viewModels()

        viewModel.highLightedArticles.observe(viewLifecycleOwner, Observer { highlightedArticles ->
            Timber.d("Mock API Articles List Response::${highlightedArticles}")
            highlightedArticlesAdapter.setAdapterData(highlightedArticles)
        })

        viewModel.otherArticles.observe(viewLifecycleOwner, Observer { otherArticles ->
            Timber.d("Mock API Articles List Response::${otherArticles}")
            otherArticlesAdapter.setAdapterData(otherArticles)
        })

        viewModel.user.observe(viewLifecycleOwner, Observer { user ->
            Timber.d("Mock API fragment User Response::${user}")
        })

        viewModel.article.observe(viewLifecycleOwner, Observer { anArticle ->
            Timber.d("Mock API Article id:1 Response::${anArticle}")
        })

        return layout
    }


}