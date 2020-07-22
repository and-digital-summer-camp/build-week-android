package com.and.newton.comms

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView


class CommsHomeFragment : Fragment() {

    companion object {
        fun newInstance() = CommsHomeFragment()
    }

    private lateinit var viewModel: CommsHomeViewModel

    private lateinit var  ctx: Context

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

      val binding = inflater.inflate(R.layout.comms_home_fragment, container, false)
      val categoryEdit = binding.findViewById<AutoCompleteTextView>(R.id.category_edit)
      val categoryEdit2 = binding.findViewById<CustomAutoTextView>(R.id.category_edit2)
        var categoryListData: List<String> = listOf<String>(
            "COVID-19",
            "Business Update",
            "Android Upskilling",
            "Mobile Native",
            "React",
            "COVID-20",
            "iOS Native"
        )
        val adapterCategories = ArrayAdapter<String>(ctx, android.R.layout.simple_spinner_item, categoryListData.toList())
        categoryEdit.setAdapter(adapterCategories)
        categoryEdit.setOnFocusChangeListener(View.OnFocusChangeListener{ view, b ->
            if(b) {categoryEdit.showDropDown()} else {categoryEdit.dismissDropDown()}

        }
        )
        categoryEdit2.setDropDownBox(categoryListData.toList())




        return binding
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CommsHomeViewModel::class.java)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.ctx = context
    }
}