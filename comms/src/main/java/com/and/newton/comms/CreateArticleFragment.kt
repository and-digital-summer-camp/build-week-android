package com.and.newton.comms

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.and.newton.comms.domain.data.Article
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_create_article.*

@AndroidEntryPoint
class CreateArticleFragment : Fragment() {

    private val viewModel : CreateArticleViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_article, container, false)
        val article = arguments?.get("article") as? Article

        if (article != null) {
            val createArticleTitle = view.findViewById<TextView>(R.id.textview_main_title)
            val editTextTitle = view.findViewById<EditText>(R.id.edittext_title)
            val editTextContent = view.findViewById<EditText>(R.id.edittext_content)

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

    private fun cancelCreateArticle() {
        val createArticleToHomeAction =
            CreateArticleFragmentDirections.actionFragmentCreateArticleToFragmentCommsHome()
        this.findNavController().navigate(createArticleToHomeAction)
    }

    private fun postNewArticle() {
        if (!edittext_title.text.isNullOrEmpty() && !edittext_content.text.isNullOrEmpty()) {
            val newArticle = Article(1, edittext_title.text.toString(), edittext_content.text.toString())

            // TODO: Error handling
            viewModel.createNewArticle(newArticle)
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