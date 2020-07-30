package com.and.newton.comms

import android.net.Uri
import android.os.Bundle
import android.view.*
import android.webkit.URLUtil
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.and.newton.common.utils.AppConstants
import com.and.newton.common.utils.AppPreferences
import com.and.newton.comms.domain.data.Article
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_view_article.view.*

class ViewArticleFragment : Fragment() {
    val args: ViewArticleFragmentArgs by navArgs()

    private var article : Article? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.admin_menu_options, menu)
        super.onCreateOptionsMenu(menu, inflater)
        AppPreferences.access_level
        if(AppPreferences.access_level == AppConstants.ROLE_USER.toString()){
            menu.clear()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

       val layout = inflater.inflate(R.layout.fragment_view_article, container, false)
        article = args.article
        if (article != null) {
            layout.viewArticleFragment_ArticleTitle.text = article!!.title
            //TODO FOR NOW ITS ARTICLE NEEDS TO BE CHANGED TO WHATEVER IS ACTUALLY REQUIRED, DONT KNOW YET THOUGH
            (activity as AppCompatActivity?)?.supportActionBar?.title = "Article"
            layout.viewArticleFragment_ArticleBody.text = article!!.content

            //TODO needs to change when we get actual picture
            if(article!!.imagePath != null && URLUtil.isValidUrl(article!!.imagePath)){
                Picasso.get()
                    .load(article!!.imagePath)
                    .fit().centerCrop().into((layout.viewArticleFragment_ArticleImage) as ImageView)
                layout.viewArticleFragment_ArticleImage.visibility = View.VISIBLE
            } else {
                layout.viewArticleFragment_ArticleImage.visibility = View.GONE
            }

            if(article!!.highlighted == true){
                layout.highlighted_image.visibility = View.VISIBLE
            }
            layout.viewArticleFragment_ArticleCategory.text = article!!.categories?.get(0)?.category?.name
        }
        return layout
    }

    //TODO FUNCTIONALITY FOR OPTION MENU
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.edit_article_option -> {
                val action = ViewArticleFragmentDirections.actionViewArticleFragmentToCreateArticleFragment(article)
                findNavController().navigate(action)
            }
            R.id.delete_article_option -> {
                //TODO DELETE SOMETHING
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
