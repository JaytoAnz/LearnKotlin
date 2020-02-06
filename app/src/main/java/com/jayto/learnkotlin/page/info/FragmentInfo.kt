package com.jayto.learnkotlin.page.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jayto.learnkotlin.R

class FragmentInfo : Fragment(){

    companion object{
        fun newInstance() : FragmentInfo {
            return FragmentInfo()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view : View = inflater.inflate(R.layout.fragment_info, container, false)


        return view
    }
}