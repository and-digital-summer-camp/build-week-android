package com.and.newton.comms.landing_page

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.and.newton.comms.R
import com.and.newton.comms.domain.data.Article
//import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.article_list_item.view.*
import javax.inject.Inject

class ArticlesAdapter @Inject constructor():
    RecyclerView.Adapter<ArticlesAdapter.ThumbnailViewHolder>() {

    private lateinit var articleDataSet:List<Article>


    fun bindData(dataSet: List<Article>) {
        articleDataSet = dataSet
    }


    class ThumbnailViewHolder(val cardView: View) : RecyclerView.ViewHolder(cardView) {
        private lateinit var article: Article

        fun bindViewData(article: Article) {
            this.article = article

//            cardView.tvArticleContent.text = article.content
//            cardView.tvCategoryLabel.text = "Label"
//            Picasso.get()
//                .load("https://images-na.ssl-images-amazon.com/images/I/810FiMQwZ5L._AC_SL1500_.jpg")
//                .resize(250, 250).centerCrop().into(cardView.ivArticleImage)
            //TOdo init the item view with the article data


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThumbnailViewHolder {
        val cardView =
            LayoutInflater.from(parent.context).inflate(R.layout.article_list_item, parent, false)


        //Todo Implement click listener on the cardview to navigate to the Arcticle/Comms View fragment

        return ThumbnailViewHolder(
            cardView
        )
    }

    override fun getItemCount(): Int = articleDataSet.size

    override fun onBindViewHolder(holder: ThumbnailViewHolder, position: Int) {
        holder.bindViewData(articleDataSet[position])
    }

}