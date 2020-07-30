package com.and.newton.comms

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.and.newton.comms.domain.data.Article
import com.and.newton.comms.domain.data.Category
import com.and.newton.comms.domain.data.CategoryHolder
import com.and.newton.shared_ui.CustomAutoCompleteTextView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.create_article_fragment.*
import kotlinx.android.synthetic.main.create_article_fragment.view.*
import timber.log.Timber

@AndroidEntryPoint
class CreateArticleFragment : Fragment() {

    private val args: CreateArticleFragmentArgs by navArgs()

    private var editArticle: Article? = null

    private val viewModel: CommsSharedViewModel by viewModels()
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

        val view = inflater.inflate(R.layout.create_article_fragment, container, false)
        (activity as AppCompatActivity?)?.supportActionBar?.setTitle(R.string.create_article_fragment_title)
        // This deals with populating the Fragment with Article data so it is an Edit page
        editArticle = args.article
        editArticle?.let { populateEditArticle(view, it) }

        val cancelButton = view.findViewById<Button>(R.id.button_cancel)
        cancelButton.setOnClickListener {
            navigateToCommsHome()
        }

        val postButton = view.findViewById<Button>(R.id.button_post)
        postButton.setOnClickListener {
            sendArticleRequest()
        }

        viewModel.categories.observe(viewLifecycleOwner, Observer { t ->
            Timber.d("Mock API Get Categories Response::${t}")

            val categoryNames: List<String?> = t.map { it.name }
            view.category_edit.setDropDownBox(categoryNames as List<String>)
        })

        return view
    }

    private fun sendArticleRequest() {
        if (!edittext_title.text.isNullOrEmpty() && !edittext_content.text.isNullOrEmpty()) {

            // TODO : Remove hardcoded IDs from category
            var categories = ArrayList<CategoryHolder>()
            if (!category_edit.text.isNullOrEmpty()) {
                val category = Category(null, category_edit.text.toString())
                categories.add(CategoryHolder(category))
            }

            if (editArticle != null) {
                updateArticle(categories)
            } else {
                createNewArticle(categories)
            }

        } else {
            if (edittext_title.text.isNullOrEmpty()) {
                edittext_title.error = "Title field is required"
            }
            if (edittext_content.text.isNullOrEmpty()) {
                edittext_content.error = "Content field is required"
            }
        }
    }

    private fun createNewArticle(categories : List<CategoryHolder>) {
        val newArticle = Article(null, edittext_title.text.toString(),
            edittext_content.text.toString(), null, null, null,
            highlighted_switch.isChecked, categories
        )

        viewModel.postArticle(newArticle).observe(viewLifecycleOwner, Observer { success ->
            Timber.d("Mock API Create New Article Response::${success}")

            handleCallResponse()
        })
    }

    private fun updateArticle(categories: List<CategoryHolder>) {
        editArticle?.title = edittext_title.text.toString()
        editArticle?.content = edittext_content.text.toString()
        editArticle?.highlighted = highlighted_switch.isChecked
        editArticle?.categories = categories

        val articleId = editArticle?.id!!
        editArticle?.let {
            viewModel.updateArticle(articleId, it).observe(viewLifecycleOwner, Observer { success ->
                Timber.d("Mock API Update Article Response::${success}")

                handleCallResponse()
            })
        }
    }

    private fun handleCallResponse() {
        // TODO: Handle different responses
        createSuccessDialog()
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
        val editTextTitle = view.findViewById<EditText>(R.id.edittext_title)
        val editTextContent = view.findViewById<EditText>(R.id.edittext_content)
        val categoryDropdown = view.findViewById<CustomAutoCompleteTextView>(R.id.category_edit)
        val highlightedSwitch = view.findViewById<Switch>(R.id.highlighted_switch)

        editTextTitle.setText(article.title)
        editTextContent.setText(article.content)
        highlightedSwitch.isChecked = article.highlighted!!
        if (!article.categories.isNullOrEmpty()) {
            // TODO: Fix this set dropdown text - how the hell do you deal with a whole list of them?
            categoryDropdown.setText(article.categories!![0].category?.name)
        }
    }

    private fun navigateToCommsHome() {
        findNavController().popBackStack()
    }


}