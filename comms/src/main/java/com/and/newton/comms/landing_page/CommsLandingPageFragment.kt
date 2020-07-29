package com.and.newton.comms.landing_page


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
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

    private lateinit var adapter: ArrayAdapter<String>

    private val viewModel: CommsSharedViewModel by viewModels()

    private lateinit var categoryFilter:Spinner
    private lateinit var ctx: Context
    private var categoryList: MutableList<String> = mutableListOf("All Categories")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    private fun updateCategoriesFilter(categories : List<String>){
        Log.d("viewLifeCycle", categories.toString())
        requireActivity().invalidateOptionsMenu()
    }
    private fun setDropDownAdapter(){
        adapter = ArrayAdapter(ctx, R.layout.dropdown_spinner, categoryList)
        adapter.setDropDownViewResource(R.layout.dropdown_spinner_selected)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.comms_landing_page_fragment, container, false)

        (activity as AppCompatActivity?)?.supportActionBar?.setTitle(R.string.comms_landing_fragment_title)

        viewModel.articles.observe(viewLifecycleOwner, Observer { articles ->
            Timber.d("Mock API all Articles List Response::${articles}")
            articlesAdapter.bindData(articles)
            layout.articles.adapter = articlesAdapter
            layout.articles.adapter?.notifyDataSetChanged()
        })

        viewModel.user.observe(viewLifecycleOwner, Observer { user ->
            Timber.d("Mock API fragment User Response::${user}")
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val item = menu.findItem(R.id.action_filter)
        categoryFilter = item.actionView as Spinner

        adapter = ArrayAdapter(ctx, R.layout.dropdown_spinner, categoryList.sorted())
        adapter.setDropDownViewResource(R.layout.dropdown_spinner_selected)

        categoryFilter.adapter = adapter
        categoryFilter.onItemSelectedListener = this
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