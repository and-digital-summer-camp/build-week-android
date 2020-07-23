package com.and.newton.comms

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.and.newton.comms.domain.data.Article
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class CommsHomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.fragment_comms_home, container, false)

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

        val createArticleButton = layout.findViewById<Button>(R.id.button_create_article)
        createArticleButton.setOnClickListener {
            openCreateArticleFragment()
        }

        val editArticleButton = layout.findViewById<Button>(R.id.button_edit_article)
        editArticleButton.setOnClickListener {
            openEditArticleFragment()
        }

        return layout
    }

    private fun openCreateArticleFragment() {
        val homeToCreateFragment =
            CommsHomeFragmentDirections
                .actionFragmentCommsHomeToFragmentCreateArticle()
        this.findNavController().navigate(homeToCreateFragment)
    }

    private fun openEditArticleFragment() {
        val article = Article(1,"title", "content")
        val homeToEditFragment =
            CommsHomeFragmentDirections.actionFragmentCommsHomeToFragmentCreateArticle()
        homeToEditFragment.article = article
        this.findNavController().navigate(homeToEditFragment)
    }


}