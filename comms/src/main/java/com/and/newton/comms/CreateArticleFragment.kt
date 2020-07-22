package com.and.newton.comms

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_create_article.*

class CreateArticleFragment : Fragment() {

    private lateinit var viewModel: CreateArticleViewModel

    companion object {
        fun newInstance() = CreateArticleFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_article, container, false)

        val article = arguments?.get("article") as? ArticleDataModel
        val createArticleTitle = view.findViewById<TextView>(R.id.textview_main_title)
        val editTextTitle = view.findViewById<EditText>(R.id.edittext_title)
        val editTextContent = view.findViewById<EditText>(R.id.edittext_content)
        if (article != null) {
            createArticleTitle.text = getString(R.string.edit_post_title)
            editTextTitle.setText(article.title)
            editTextContent.setText(article.content)
        }

        val cancelButton = view.findViewById<Button>(R.id.button_cancel)
        cancelButton.setOnClickListener {
            cancelCreateArticle()
        }

        val postButton = view.findViewById<Button>(R.id.button_post)
        postButton.setOnClickListener {
            postNewArticle()
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CreateArticleViewModel::class.java)
    }

    private fun cancelCreateArticle() {
        val createArticleToHomeAction =
            CreateArticleFragmentDirections.actionFragmentCreateArticleToFragmentArticlesHome()
        this.findNavController().navigate(createArticleToHomeAction)
    }

    private fun postNewArticle() {
        if (!edittext_title.text.isNullOrEmpty() && !edittext_content.text.isNullOrEmpty()) {
            //TODO: Add implementation to send off to back end
        } else {
            if (edittext_title.text.isNullOrEmpty()) {
                edittext_title.error = "Title field is required"
            }
            if (edittext_content.text.isNullOrEmpty()) {
                edittext_content.error = "Content field is required"
            }
        }

    }

}