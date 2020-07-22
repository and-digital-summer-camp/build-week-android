package com.and.newton.comms

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

class ArticlesHomeFragment : Fragment() {

    private lateinit var viewModel: ArticlesHomeViewModel

    companion object {
        fun newInstance() = ArticlesHomeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_articles_home, container, false)

        val createArticleButton = view.findViewById<Button>(R.id.button_create_article)
        createArticleButton.setOnClickListener {
            openCreateArticleFragment()
        }

        val editArticleButton = view.findViewById<Button>(R.id.button_edit_article)
        editArticleButton.setOnClickListener {
            openEditArticleFragment()
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ArticlesHomeViewModel::class.java)
    }

    private fun openCreateArticleFragment() {
        val homeToCreateFragment =
            ArticlesHomeFragmentDirections
                .actionFragmentArticlesHomeToFragmentCreateArticle()
        this.findNavController().navigate(homeToCreateFragment)
    }

    private fun openEditArticleFragment() {
        val article = ArticleDataModel("title", "content")
        val homeToEditFragment =
            ArticlesHomeFragmentDirections.actionFragmentArticlesHomeToFragmentCreateArticle()
        homeToEditFragment.article = article
        this.findNavController().navigate(homeToEditFragment)
    }

}