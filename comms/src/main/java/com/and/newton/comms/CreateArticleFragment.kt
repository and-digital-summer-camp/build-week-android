package com.and.newton.comms

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
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
import com.cloudinary.android.MediaManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.create_article_fragment.*
import kotlinx.android.synthetic.main.create_article_fragment.view.*
import timber.log.Timber


@AndroidEntryPoint
class CreateArticleFragment : Fragment() {

    private val viewModel: CommsSharedViewModel by viewModels()
    private val UPLOAD_IMAGE: Int = 100
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.create_article_fragment, container, false)

        // This deals with populating the Fragment with Article data so it is an Edit page
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

        val uploadImage: Button = view.findViewById<Button>(R.id.uploadImage)
        uploadImage.setOnClickListener() {
            uploadImage()
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
                null, null, highlighted_checkBox.isChecked, categories
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
        val highlightedCheckbox = view.findViewById<CheckBox>(R.id.highlighted_checkBox)

        createArticleTitle.text = getString(R.string.edit_post_title)
        editTextTitle.setText(article.title)
        editTextContent.setText(article.content)
        highlightedCheckbox.isChecked = article.highlighted!!
        if (!article.categories.isNullOrEmpty()) {
            // TODO: Fix this set dropdown text - how the hell do you deal with a whole list of them?
            categoryDropdown.setText(article.categories[0].name)
        }
    }

    private fun navigateToCommsHome() {
        val uri = Uri.parse("App://nav_comms")
        findNavController().navigate(uri)
    }

    private fun uploadImage() {
        var intent: Intent = Intent(Intent.ACTION_GET_CONTENT, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, this.UPLOAD_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == this.UPLOAD_IMAGE) {
            this.requireActivity().findViewById<Button>(R.id.uploadImage).text =  data?.data.toString()
            cloudinary(data?.data.toString())
        }
    }

    private fun cloudinary(filePath: String) {
        val requestId = MediaManager.get().upload("filePath")
            .unsigned("preset1")
            .option("resource_type", "video")
            .option("folder", "my_folder/my_sub_folder/")
            .option("public_id", "my_dog")
            .option("overwrite", true)
           // .option("notification_url", "https://mysite.example.com/notify_endpoint")
            .dispatch()
    }


}