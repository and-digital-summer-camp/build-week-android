package com.and.newton.comms

import android.R
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.content.ContextCompat.getSystemService

@SuppressLint("AppCompatCustomView")
class CustomAutoTextView @JvmOverloads constructor(context:Context, attributeSet: AttributeSet): AutoCompleteTextView(context, attributeSet) {
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