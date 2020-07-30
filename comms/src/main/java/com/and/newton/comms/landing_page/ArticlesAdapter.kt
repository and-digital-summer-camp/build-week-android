package com.and.newton.comms.landing_page

import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.and.newton.comms.R
import com.and.newton.comms.databinding.ArticleListItemBinding
import com.and.newton.comms.domain.data.Article
import com.squareup.picasso.Picasso
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import timber.log.Timber
//import com.squareup.picasso.Picasso
import java.util.*
import javax.inject.Inject


class ArticlesAdapter @Inject constructor():
    RecyclerView.Adapter<ArticlesAdapter.ThumbnailViewHolder>(), Filterable {




    private var articleDataSet:List<Article> = listOf()

    private var articleFilteredDataSet:List<Article> = listOf()


    var isArticleEmpty: ConflatedBroadcastChannel<Boolean> = ConflatedBroadcastChannel()
        // getter
        get() = field

        // setter
        set(value) {
            field = value
        }

    fun bindData(dataSet: List<Article>) {
        articleDataSet = dataSet
        articleFilteredDataSet= articleDataSet
        onDataSetUpdated()
    }

    fun onDataSetUpdated() {
        isArticleEmpty.offer(articleFilteredDataSet.size == 0)
    }




    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterCategory = constraint.toString()
                var filteredDataSet: List<Article>

                val filterResults = FilterResults()
                if (filterCategory.isEmpty() || ("All Categories").toLowerCase(Locale.ROOT).contains(filterCategory.toLowerCase(Locale.ROOT))) {
                    filteredDataSet = articleDataSet
                }
                else {
                    filteredDataSet =  articleDataSet.filter { article:Article ->
                        var categoryFound = false
                        article.categories?.forEach{
                            if((it.category?.name?:"").toLowerCase(Locale.ROOT).contains(filterCategory.toLowerCase(Locale.ROOT))) {
                                categoryFound = true
                            }
                        }
                        categoryFound
                    }
                }
                filterResults.values = filteredDataSet
                filterResults.count = filteredDataSet.size;
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                articleFilteredDataSet = (results?.values as? List<Article> ) ?: articleDataSet
                notifyDataSetChanged()

                onDataSetUpdated()

            }

        }
    }

    class ThumbnailViewHolder(val binding: ArticleListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var article: Article


        fun bindViewData(article: Article) {
            binding.article = article
            if(article.highlighted == true) {
                binding.articleConstraintLayout.setBackgroundResource(R.drawable.article_highlight_border)
                binding.ivHighlight.visibility = View.VISIBLE
            } else {
                binding.articleConstraintLayout.background = null
                binding.ivHighlight.visibility = View.GONE
            }

            binding.executePendingBindings()

            binding.root.setOnClickListener{
                val action = CommsLandingPageFragmentDirections.actionCommsLandingPageFragmentToViewArticleFragment(article)
                binding.root.findNavController().navigate(action)

            }

            if(article.imagePath!=null && URLUtil.isValidUrl(article.imagePath)) {
                Picasso.get()
                    .load(article.imagePath)
                    .resize(144, 144).centerCrop().into(binding.articleImage)
                binding.articleImage.visibility = View.VISIBLE
            }else{
                binding.articleImage.visibility = View.GONE
            }


        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThumbnailViewHolder {


        //Todo Implement click listener on the cardview to navigate to the Arcticle/Comms View fragment

        return ThumbnailViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.article_list_item, parent, false
            )
        )

    }

    override fun getItemCount(): Int  = articleFilteredDataSet.size


    override fun onBindViewHolder(holder: ThumbnailViewHolder, position: Int) {
        holder.bindViewData(articleFilteredDataSet[position])
    }

}
