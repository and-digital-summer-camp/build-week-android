package com.and.newton.shared_ui

import android.R
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@SuppressLint("AppCompatCustomView")
class CustomAutoCompleteTextView @JvmOverloads constructor(context:Context, attributeSet: AttributeSet): AutoCompleteTextView(context, attributeSet) {
    lateinit var adapterCategories: ArrayAdapter<String>
    fun setDropDownBox(data:List<String>) {
        this.adapterCategories = ArrayAdapter<String>(context, R.layout.simple_spinner_item, data)
        this.setAdapter(adapterCategories)
        Log.d("setDropDownBox", "here")
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        if(focused) {this.showDropDown()
            Log.d("onFocusChange", "here")
        } else {this.dismissDropDown()
            Log.d("onFocusChange", "else statement")
        }
    }

}