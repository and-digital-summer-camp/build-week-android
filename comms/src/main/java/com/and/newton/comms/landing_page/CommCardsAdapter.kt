package com.and.newton.comms.landing_page

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.and.newton.comms.R
import com.and.newton.comms.domain.data.Article
import javax.inject.Inject

class CommCardsAdapter @Inject constructor():
    RecyclerView.Adapter<CommCardsAdapter.ThumbnailViewHolder>() {

    private lateinit var articleDataSet:List<Article>


    fun setAdapterData(dataSet: List<Article>) {
        articleDataSet = dataSet
    }


    class ThumbnailViewHolder(val cardView: View) : RecyclerView.ViewHolder(cardView) {
        private lateinit var article: Article

        fun bindViewData(article: Article) {
            this.article = article
            //TOdo init the item view with the article data

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThumbnailViewHolder {
        val cardView =
            LayoutInflater.from(parent.context).inflate(R.layout.comms_thumbnail_item_view, parent, false)

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