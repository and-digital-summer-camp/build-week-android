package com.and.newton.comms

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.and.newton.common.utils.AppPreferences
import com.and.newton.common.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_view_article.view.*

class ViewArticleFragment : Fragment() {

    val args: ViewArticleFragmentArgs by navArgs()

    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var adminMenu: Spinner
    private var adminOptions: MutableList<String> = mutableListOf("Edit" , "Delete")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.admin_menu_options, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

       val layout = inflater.inflate(R.layout.fragment_view_article, container, false)
        val article = args.article
        if (article != null) {
            layout.viewArticleFragment_ArticleTitle.text = article.title
            //TODO FOR NOW ITS ARTICLE NEEDS TO BE CHANGED TO WHATEVER IS ACTUALLY REQUIRED, DONT KNOW YET THOUGH
            (activity as AppCompatActivity?)?.supportActionBar?.title = "Article"
            layout.viewArticleFragment_ArticleBody.text = article.content

            //TODO needs to change when we get actual picture
            if(article.imagePath != null){
                Log.d("articleimg", article.imagePath)
                val imagePath: Uri = Uri.parse(article.imagePath)
                layout.viewArticleFragment_ArticleImage.setImageURI(imagePath)
            } else {
                //just an empty border image
                layout.viewArticleFragment_ArticleImage.setBackgroundResource(R.drawable.highlight)
            }
            layout.viewArticleFragment_ArticleCategory.text = article.categories?.get(0)?.category?.name



        }


        return layout
    }

    //TODO FUNCTIONALITY FOR OPTION MENU
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.edit_article_option -> {
                //TODO NAVIGATE SOMEWHERE
            }
            R.id.delete_article_option -> {
                //TODO DELETE SOMETHING
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
