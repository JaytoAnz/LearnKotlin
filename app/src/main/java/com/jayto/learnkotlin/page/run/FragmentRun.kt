package com.jayto.learnkotlin.page.run

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jayto.learnkotlin.R

class FragmentRun : Fragment(){

    companion object{
        fun newInstance() : FragmentRun {
            return FragmentRun()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view : View = inflater.inflate(R.layout.fragment_run, container, false)


        return view
    }
}