package com.and.newton.comms

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
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
import com.and.newton.comms.network.ApiData
import com.and.newton.comms.network.DataStatus
import com.and.newton.shared_ui.CustomAutoCompleteTextView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.create_article_fragment.*
import kotlinx.android.synthetic.main.create_article_fragment.view.*
import retrofit2.Response

@AndroidEntryPoint
class CreateArticleFragment : Fragment() {

    private val args: CreateArticleFragmentArgs by navArgs()

    private var updateArticle: Article? = null

    private var isNewCategory: Boolean = true

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
        updateArticle = args.article
        updateArticle?.let {
            (activity as AppCompatActivity?)?.supportActionBar?.title = "Edit Article"
            populateEditArticle(view, it)
        }

        val cancelButton = view.findViewById<Button>(R.id.button_cancel)
        cancelButton.setOnClickListener {
            navigateToCommsHome()
        }

        val postButton = view.findViewById<Button>(R.id.button_post)
        postButton.setOnClickListener {
            postArticleRequest()
        }

        viewModel.categories.observe(viewLifecycleOwner, Observer { categoryNames ->
            initAUtoCompleteCategoryView(categoryNames as List<String>, view)
        })

        return view
    }

    private fun initAUtoCompleteCategoryView(categoryNames: List<String>, view: View) {
        view.category_edit.setDropDownBox(categoryNames)

        view.category_edit.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                val selectedItem = parent.getItemAtPosition(position).toString()
                isNewCategory = !categoryNames.contains(selectedItem)
            }
    }

    private fun postArticleRequest() {
        if (!edittext_title.text.isNullOrEmpty() && !edittext_content.text.isNullOrEmpty() && !category_edit.text.isNullOrEmpty()) {

            val categories = ArrayList<CategoryHolder>()
            val categoryName: String = category_edit.text.toString()
            val category = Category(null, categoryName)
            categories.add(CategoryHolder(category))

            if (isNewCategory) {
                createCategory(categoryName, categories)
            } else {
                updateOrCreateArticle(categories)
            }

        } else {
            if (edittext_title.text.isNullOrEmpty()) {
                edittext_title.error = "Title field is required"
            }
            if (edittext_content.text.isNullOrEmpty()) {
                edittext_content.error = "Content field is required"
            }
            if (category_edit.text.isNullOrEmpty()) {
                category_edit.error = "Category field is required"
            }
        }
    }

    private fun createCategory(categoryName: String, categories: List<CategoryHolder>) {
        viewModel.createCategory(Category(null, categoryName))
            .observe(viewLifecycleOwner, Observer {
                if (it.status == DataStatus.SUCCESS) {
                    updateOrCreateArticle(categories)
                } else {
                    category_edit.error = "Error creating new category"
                }
            })

    }

    private fun updateOrCreateArticle(categories: List<CategoryHolder>) {
        if (updateArticle != null) {
            updateArticle(categories)
        } else {
            createNewArticle(categories)
        }
    }

    private fun createNewArticle(categories: List<CategoryHolder>) {
        val newArticle = Article(
            null, edittext_title.text.toString(),
            edittext_content.text.toString(), edittext_image.text?.toString(), null, null,
            highlighted_switch.isChecked, categories
        )

        viewModel.postArticle(newArticle).observe(viewLifecycleOwner, Observer {
            handleApiResponse(it, false)
        })

    }

    private fun updateArticle(categories: List<CategoryHolder>) {
        updateArticle?.title = edittext_title.text.toString()
        updateArticle?.content = edittext_content.text.toString()
        updateArticle?.highlighted = highlighted_switch.isChecked
        updateArticle?.categories = categories
        updateArticle?.imagePath = edittext_image.text?.toString()

        val articleId = updateArticle?.id!!
        updateArticle?.let { updateArticle ->
            viewModel.updateArticle(articleId, updateArticle)
                .observe(viewLifecycleOwner, Observer {
                    handleApiResponse(it, true)
                })
        }
    }

    private fun handleApiResponse(response: ApiData<Article>, isUpdateRequest: Boolean) {
        if (response.status == DataStatus.SUCCESS) {
            createSuccessDialog(isUpdateRequest)
        } else {
            createErrorDialog(response.responseCode!!, response.message!!)
        }
    }

    private fun createSuccessDialog(isUpdateRequest: Boolean) {
        val builder = activity?.let { AlertDialog.Builder(it) }

        if (isUpdateRequest) {
            builder?.setTitle("Article Update")
            builder?.setMessage("The Article has been successfully updated.")
        } else {
            builder?.setTitle("Article Created")
            builder?.setMessage("A new Article has been successfully created.")
        }

        builder?.setPositiveButton(android.R.string.yes) { _, _ ->
            navigateToCommsHome()
        }
        builder?.show()
    }

    private fun createErrorDialog(code: Int, message: String) {
        val builder = activity?.let { AlertDialog.Builder(it) }

        builder?.setTitle("Error")
        builder?.setMessage("A Network Error has occurred: $code - $message")

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
        val editImageContent = view.findViewById<EditText>(R.id.edittext_image)

        editTextTitle.setText(article.title)
        editTextContent.setText(article.content)
        editImageContent.setText(article.imagePath)

        highlightedSwitch.isChecked = article.highlighted == true
        if (!article.categories.isNullOrEmpty()) {
            // TODO: Fix this set dropdown text - how the hell do you deal with a whole list of them?
            categoryDropdown.setText(article.categories!![0].category?.name)
            isNewCategory = false
        }
    }

    private fun navigateToCommsHome() {
        findNavController().popBackStack()
    }


}