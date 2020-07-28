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
import com.and.newton.comms.domain.data.Category
import com.and.newton.shared_ui.CustomAutoCompleteTextView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_create_article.*
import kotlinx.android.synthetic.main.fragment_create_article.view.*
import retrofit2.HttpException
import timber.log.Timber

@AndroidEntryPoint
class CreateArticleFragment : Fragment() {

    private val viewModel: CommsSharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_create_article, container, false)

        val article = arguments?.get("article") as? Article
        if (article != null) {
            populateEditArticle(view, article)
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

            val categoryNames: List<String?> = t.map { it.name }
            view.category_edit.setDropDownBox(categoryNames as List<String>)
        })

        return view
    }

    private fun postNewArticle() {
        if (!edittext_title.text.isNullOrEmpty() && !edittext_content.text.isNullOrEmpty()) {

            // TODO : Remove hardcoded IDs from category
            var categories = ArrayList<Category>()
            if (!category_edit.text.isNullOrEmpty()) {
                val category = Category(1, category_edit.text.toString())
                categories.add(category)
            }

            // TODO : Remove hardcoded IDs from article
            val newArticle = Article(
                1,
                edittext_title.text.toString(),
                edittext_content.text.toString(),
                null, null, false, categories
            )

            viewModel.postArticle(newArticle).observe(viewLifecycleOwner, Observer { article ->
                Timber.d("Mock API Post Article Response::${article}")

                // TODO: Handle status codes
                createSuccessDialog()
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

    private fun createSuccessDialog() {
        val builder = activity?.let { AlertDialog.Builder(it) }

        builder?.setTitle("Article Created")
        builder?.setMessage("A new Article has been successfully created")

        builder?.setPositiveButton(android.R.string.yes) { _, _ ->
            navigateToCommsHome()
        }
        builder?.show()
    }

    private fun createErrorDialog() {
        val builder = activity?.let { AlertDialog.Builder(it) }

        builder?.setTitle("Error")
        builder?.setMessage("A Network Error has occurred in creating the Article. Please try again")

        val alertDialog = builder?.show()
        builder?.setPositiveButton(android.R.string.yes) { _, _ ->
            alertDialog?.dismiss()
        }
    }

    private fun populateEditArticle(view: View, article: Article) {
        val createArticleTitle = view.findViewById<TextView>(R.id.textview_main_title)
        val editTextTitle = view.findViewById<EditText>(R.id.edittext_title)
        val editTextContent = view.findViewById<EditText>(R.id.edittext_content)
        val categoryDropdown = view.findViewById<CustomAutoCompleteTextView>(R.id.category_edit)

        createArticleTitle.text = getString(R.string.edit_post_title)
        editTextTitle.setText(article.title)
        editTextContent.setText(article.content)
        if (!article.categories.isNullOrEmpty()) {
            // TODO: Fix this set dropdown text - how the hell do you deal with a whole list of them?
            categoryDropdown.setText(article.categories[0].name)
        }
    }

    private fun navigateToCommsHome() {
        val createArticleToLandingAction =
            CreateArticleFragmentDirections.actionFragmentCreateArticleToCommsLandingPageFragment()

        findNavController().navigate(R.id.action_fragment_create_article_to_commsLandingPageFragment)
    }


}