package com.and.newton.comms.landing_page

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.and.newton.comms.CommsSharedViewModel
import com.and.newton.comms.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.comms_landing_page_fragment.view.*
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class CommsLandingPageFragment : Fragment() {

    @Inject
    lateinit var articlesAdapter: ArticlesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.comms_landing_page_fragment, container, false)


        val viewModel: CommsSharedViewModel by viewModels()

        viewModel.articles.observe(viewLifecycleOwner, Observer { articles ->
            Timber.d("Mock API all Articles List Response::${articles}")
            articlesAdapter.bindData(articles)
            layout.articles.adapter = articlesAdapter
            layout.articles.adapter?.notifyDataSetChanged()
        })

        viewModel.user.observe(viewLifecycleOwner, Observer { user ->
            Timber.d("Mock API fragment User Response::${user}")
        })

        viewModel.article.observe(viewLifecycleOwner, Observer { anArticle ->
            Timber.d("Mock API Article id:1 Response::${anArticle}")
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