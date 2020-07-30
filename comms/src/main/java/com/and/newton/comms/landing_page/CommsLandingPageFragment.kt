package com.and.newton.comms.landing_page


import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.and.newton.comms.CommsSharedViewModel
import com.and.newton.comms.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.comms_landing_page_fragment.*
import kotlinx.android.synthetic.main.comms_landing_page_fragment.view.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@AndroidEntryPoint
class CommsLandingPageFragment : Fragment(), AdapterView.OnItemSelectedListener {

    @Inject
    lateinit var articlesAdapter: ArticlesAdapter

    private var adapter: ArrayAdapter<String>? = null

    private val viewModel: CommsSharedViewModel by viewModels()

    private var categoryFilter:Spinner? = null

    private var categoryList: MutableList<String> = mutableListOf("All Categories")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    private fun initSwipeToRefresh(){
        itemsswipetorefresh.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(requireContext(), R.color.color_secondary_pink))
        itemsswipetorefresh.setColorSchemeColors(ContextCompat.getColor(requireContext(), R.color.color_primary_shrine_pink))

        itemsswipetorefresh.setOnRefreshListener {
            viewModel.fetchArticles().observe(viewLifecycleOwner, Observer { articlesData ->
                Timber.d("Mock API called again all Articles List Response::${articlesData}")
                articlesAdapter.bindData(articlesData)
                articles.adapter = articlesAdapter
                articlesAdapter.filter.filter(categoryList.sorted()[0])
                articles.adapter?.notifyDataSetChanged()
                itemsswipetorefresh.isRefreshing = false
            })
        }
    }

    private fun updateCategoriesFilter(categories : List<String>){
        setDropDownAdapter(categories)
        categoryFilter?.adapter = adapter
        adapter?.notifyDataSetChanged()
    }

    private fun setDropDownAdapter(categories : List<String>){
        adapter  = context?.let { ArrayAdapter(it, R.layout.dropdown_spinner, categories) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val layout = inflater.inflate(R.layout.comms_landing_page_fragment, container, false)
//        if(AppPreferences.access_level == "Admin"){
//            layout.editBtn.visibility = View.VISIBLE
//        } else {
//            layout.editBtn.visibility = View.GONE
//        }


        viewModel.articles.observe(viewLifecycleOwner, Observer { articles ->
            Timber.d("Mock API all Articles List Response::${articles}")
            articlesAdapter.bindData(articles)
            layout.articles.adapter = articlesAdapter
            articlesAdapter.filter.filter(categoryList.sorted()[0])
            layout.articles.adapter?.notifyDataSetChanged()
        })

        viewModel.article.observe(viewLifecycleOwner, Observer { anArticle ->
            Timber.d("Mock API Article id:1 Response::${anArticle}")
        })

        viewModel.categories.observe(viewLifecycleOwner, Observer { categories ->
            Timber.d("Mock API all categories List Response::${categories}")
            categoryList = categories.map {
                it.name?:"N/A"
            } as MutableList<String>

            categoryList.add("All Categories")

            updateCategoriesFilter(categoryList.sorted())
        })

        return layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSwipeToRefresh()
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val item = menu.findItem(R.id.action_filter)
        categoryFilter = item.actionView as Spinner

        setDropDownAdapter(listOf("All"))
        adapter?.setDropDownViewResource(R.layout.dropdown_spinner_selected)
        categoryFilter?.onItemSelectedListener = this
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        Timber.d("No option selected")
    }

    override fun onItemSelected(spinnerAdapter: AdapterView<*>?, itemView: View?, option: Int, p3: Long) {
        articlesAdapter.filter.filter(categoryList.sorted()[option])
        articlesAdapter.notifyDataSetChanged()

        MainScope().launch {
            articlesAdapter.isArticleEmpty.consumeEach {
                updateArticleListView(it)
            }
        }
    }

    private fun updateArticleListView (isEmpty:Boolean) {
        if(isEmpty) {
            noArticleItemCardView.visibility = View.VISIBLE
            articles.visibility = View.GONE
        }
        else {
            noArticleItemCardView.visibility = View.GONE
            articles.visibility = View.VISIBLE
        }
    }

}