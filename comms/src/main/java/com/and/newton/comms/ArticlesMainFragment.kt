package com.and.newton.comms

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


/**
 * A simple [Fragment] subclass.
 * Use the [ArticlesMainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ArticlesMainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_articles_main, container, false)
    }

    companion object {
        fun newInstance() = ArticlesMainFragment()
    }
}