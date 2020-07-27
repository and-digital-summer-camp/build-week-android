package com.and.newton.comms.landing_page

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.and.newton.comms.R
import com.and.newton.comms.databinding.ArticleListItemBinding
import com.and.newton.comms.domain.data.Article
import com.and.newton.comms.domain.data.Category
//import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.article_list_item.view.*
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


class ArticlesAdapter @Inject constructor():
    RecyclerView.Adapter<ArticlesAdapter.ThumbnailViewHolder>(), Filterable {

    private lateinit var articleDataSet:List<Article>

    private var articleFilteredDataSet:List<Article> = listOf()

    fun bindData(dataSet: List<Article>) {
        articleDataSet = dataSet
        articleFilteredDataSet= articleDataSet
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterCategory = constraint.toString()
                if (filterCategory.isEmpty() || ("All").toLowerCase(Locale.ROOT).contains(filterCategory.toLowerCase(Locale.ROOT))) {
                    articleFilteredDataSet = articleDataSet
                }
                else {
                    articleFilteredDataSet =  articleDataSet.filter { article:Article ->
                        var categoryFound = false
                        article.categories?.forEach{
                            if((it.name?:"").toLowerCase(Locale.ROOT).contains(filterCategory.toLowerCase(Locale.ROOT))) {
                                categoryFound = true
                            }
                        }
                        categoryFound
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = articleFilteredDataSet
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                articleFilteredDataSet = results?.values as ArrayList<Article>
                notifyDataSetChanged()
            }

        }
    }

    class ThumbnailViewHolder(val binding: ArticleListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var article: Article


        fun bindViewData(article: Article) {
            binding.article = article
//            binding.txtArticleLabel.visibility = if(article.highlighted == true) View.VISIBLE else View.GONE
            binding.executePendingBindings()

//            cardView.txtArticleDesc.text = article.content
//            cardView.txtArticleLabel.text = article.categories?.get(0)?.name

//            Picasso.get()
//                .load("https://images-na.ssl-images-amazon.com/images/I/810FiMQwZ5L._AC_SL1500_.jpg")
//                .resize(250, 250).centerCrop().into(cardView.ivArticleImage)
            //TOdo init the item view with the article data


        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThumbnailViewHolder {
        val cardView =
            LayoutInflater.from(parent.context)
        val binding = ArticleListItemBinding.inflate(cardView)

        //Todo Implement click listener on the cardview to navigate to the Arcticle/Comms View fragment

        return ThumbnailViewHolder(
            binding
        )
    }

    override fun getItemCount(): Int = articleFilteredDataSet.size

    override fun onBindViewHolder(holder: ThumbnailViewHolder, position: Int) {
        holder.bindViewData(articleFilteredDataSet[position])
    }

}