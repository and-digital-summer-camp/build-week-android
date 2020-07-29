package com.and.newton.comms

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_view_article.view.*

class ViewArticleFragment : Fragment() {

    val args: ViewArticleFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val layout = inflater.inflate(R.layout.fragment_view_article, container, false)
        val article = args.article
        if (article != null) {
            layout.viewArticleFragment_ArticleTitle.text = article.title
            layout.viewArticleFragment_ArticleBody.text = article.content
            layout.viewArticleFragment_ArticleCategory.text = article.categories?.get(0)?.name ?: "no category"
        }
        activity?.invalidateOptionsMenu()

        return layout
    }



}
