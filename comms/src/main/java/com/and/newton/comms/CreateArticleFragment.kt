package com.and.newton.comms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.and.newton.comms.domain.data.Article
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_create_article.*
import kotlinx.android.synthetic.main.fragment_create_article.view.*
import retrofit2.HttpException
import timber.log.Timber
import java.util.stream.Collectors

@AndroidEntryPoint
class CreateArticleFragment : Fragment() {

    private val viewModel : CommsHomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_create_article, container, false)

        val article = arguments?.get("article") as? Article

        val createArticleTitle = view.findViewById<TextView>(R.id.textview_main_title)
        val editTextTitle = view.findViewById<EditText>(R.id.edittext_title)
        val editTextContent = view.findViewById<EditText>(R.id.edittext_content)
        val categoryDropdown = view.findViewById<CustomAutoCompleteTextView>(R.id.category_edit)
        
        if (article != null) {
            createArticleTitle.text = getString(R.string.edit_post_title)
            editTextTitle.setText(article.title)
            editTextContent.setText(article.content)
            if (article.category != null) {
                categoryDropdown.setText(article.category.categoryName)
            }
        }

        val cancelButton = view.findViewById<Button>(R.id.button_cancel)
        cancelButton.setOnClickListener {
            navigateToCommsHome()
        }

        val postButton = view.findViewById<Button>(R.id.button_post)
        postButton.setOnClickListener {
            postNewArticle()
        }

        viewModel.categories.observe(viewLifecycleOwner, Observer { t ->
            Timber.d("Mock API Get Categories Response::${t}")

            val categoryNames: List<String> = t.map { it.categoryName }
            view.category_edit.setDropDownBox(categoryNames)
        })

        return view
    }

    private fun postNewArticle() {
        if (!edittext_title.text.isNullOrEmpty() && !edittext_content.text.isNullOrEmpty()) {

            // TODO : Remove hardcoded IDs from category
            var category : Category ? = null
            if (!category_edit.text.isNullOrEmpty()) {
                category = Category(1, category_edit.text.toString())
            }

            // TODO : Remove hardcoded IDs from article
            val newArticle = Article(1, edittext_title.text.toString(), edittext_content.text.toString(), category)

            viewModel.postArticle(newArticle).observe(viewLifecycleOwner, Observer { article ->
                Timber.d("Mock API Post Article Response::${article}")

                // TODO: Handle status codes
                createAlertDialog()
                //
            })
        } else {
            if (edittext_title.text.isNullOrEmpty()) {
                edittext_title.error = "Title field is required"
            }
            if (edittext_content.text.isNullOrEmpty()) {
                edittext_content.error = "Content field is required"
            }
        }
    }

    private fun createAlertDialog() {
        val builder = activity?.let { AlertDialog.Builder(it) }

        builder?.setTitle("Article Created")
        builder?.setMessage("A new Article has been successfully created")

        builder?.setPositiveButton(android.R.string.yes) { _, _ ->
            navigateToCommsHome()
        }
        builder?.show()
    }

    private fun navigateToCommsHome() {
        val createArticleToHomeAction =
            CreateArticleFragmentDirections.actionFragmentCreateArticleToFragmentCommsHome()
        this.findNavController().navigate(createArticleToHomeAction)
    }


}