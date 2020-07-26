package com.and.newton.navigation

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout

class NavLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle){
    init {
        LayoutInflater.from(context)
            .inflate(R.layout.nav_layout, this, true)

    }



        fun loginNavigateToComms(){
//            findNavController().navigate(R.id.action_login_to_nav_comms)
        }


}