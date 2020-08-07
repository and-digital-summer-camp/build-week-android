package com.and.newton.comms

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.loader.content.AsyncTaskLoader
import androidx.navigation.fragment.findNavController
import com.and.newton.comms.domain.data.Article
import com.and.newton.comms.domain.data.Category
import com.and.newton.shared_ui.CustomAutoCompleteTextView
import com.cloudinary.android.MediaManager
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.create_article_fragment.*
import kotlinx.android.synthetic.main.create_article_fragment.view.*
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.lang.StringBuilder


@AndroidEntryPoint
class CreateArticleFragment : Fragment() {

    private val viewModel: CommsSharedViewModel by viewModels()
    private val UPLOAD_IMAGE: Int = 100
    private lateinit var uploadedImageUrl: String
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

        var imageView: ImageView  = this.requireActivity().findViewById<ImageView>(R.id.articleImage)
        imageView.isVisible = false

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
            uploadPhoto(this.requireActivity(), Uri.parse(data?.data.toString()))
        }
    }

    fun uploadPhoto(context: Context, uri: Uri) {
        var thread: Thread =  Thread {
            var uplloadPhotoTask: UploadPhotoTask =  UploadPhotoTask(context, uri)
            uplloadPhotoTask.startLoading()
            this.uploadedImageUrl = uplloadPhotoTask.getUploadedImageUrl().toString()
        }
        thread.start()
        thread.join()
        var imageView: ImageView  = this.requireActivity().findViewById<ImageView>(R.id.articleImage)
        imageView.isVisible = true
        Picasso.get().load(this.uploadedImageUrl).into(imageView)
    }


    private class UploadPhotoTask(context: Context, private var uri: Uri): AsyncTaskLoader<String>(context) {
        private var uploadedImageUrl: String? = null
        override fun loadInBackground(): String? {
             var config: Map<String, String> = mapOf("CLOUDINARY_URL" to "cloudinary://942154141375788:QKzIFmRiO3qkf1UsKuz2P6RTyok@newton-summer-camp",
            "api_key" to "942154141375788",
            "api_secret" to "QKzIFmRiO3qkf1UsKuz2P6RTyok",
            "cloud_name" to "newton-summer-camp"
            )
            var mimeType: String? = context.contentResolver.getType(uri)?.toLowerCase()?.replace("/",".")
            var filePath = StringBuilder().append(context.cacheDir).append(File.separator).append(mimeType)
            var file: File = File (filePath.toString())
            if (!file.exists()) {
                file.createNewFile()
            }
            var inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            if (inputStream != null) {
                copyStreamToFile(inputStream, file)
            }
            MediaManager.init(context, config);
            var map: Map<String, String> = HashMap()
            map = mapOf("resource_type" to "image", "folder" to "android")
            var updatedMap = MediaManager.get().cloudinary.uploader()
                .upload(file, map)
            file.delete()
            this.uploadedImageUrl = updatedMap.get("secure_url").toString()
            Log.d("map", updatedMap.toString())
            return null;
        }

        override fun onStartLoading() {
            loadInBackground();
        }

        fun getUploadedImageUrl(): String? {
            return this.uploadedImageUrl
        }

        fun copyStreamToFile(inputStream: InputStream, outputFile: File) {
            inputStream.use { input ->
                val outputStream = FileOutputStream(outputFile)
                outputStream.use { output ->
                    val buffer = ByteArray(4 * 1024) // buffer size
                    while (true) {
                        val byteCount = input.read(buffer)
                        if (byteCount < 0) break
                        output.write(buffer, 0, byteCount)
                    }
                    output.flush()
                }
            }
        }

    }




}