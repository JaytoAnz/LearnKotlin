package com.jayto.learnkotlin.page.beranda

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.jayto.learnkotlin.*
import com.jayto.learnkotlin.apiservice.ApiService
import com.jayto.learnkotlin.apiservice.ConfigApi
import com.jayto.learnkotlin.model.Materi
import com.jayto.learnkotlin.page.home.MainPresenter
import com.jayto.learnkotlin.page.home.MainView
import com.jayto.learnkotlin.utils.SwipeToDeleteCallback
import java.lang.ClassCastException

class FragmentBeranda : Fragment(),
        MainView.Response, AdapterMateri.AdapterListener{

    companion object{
        fun newInstance() : FragmentBeranda {
            return FragmentBeranda()
        }
    }

    private lateinit var adapterMateri : AdapterMateri
    private lateinit var rvRecyclerView: RecyclerView
    private lateinit var lProgressbar : LinearLayout
    private lateinit var layoutManager : LinearLayoutManager
    private lateinit var mApiService : ApiService
    private lateinit var presenter: MainPresenter
    private lateinit var btnPlus : FloatingActionButton
    private lateinit var callback : Callback

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view : View = inflater.inflate(R.layout.fragment_beranda, container, false)

        mApiService = ConfigApi.getAPIService()!!

        presenter = MainPresenter(this)
        rvRecyclerView = view.findViewById(R.id.rv_tv)
        lProgressbar = view.findViewById(R.id.llProgressBar)
        btnPlus = view.findViewById(R.id.btnTambah)

        presenter.getAllData(mApiService)

        layoutManager = LinearLayoutManager(activity)
        rvRecyclerView.layoutManager = layoutManager
        adapterMateri = AdapterMateri(this)
        rvRecyclerView.adapter = adapterMateri

        val swipeHandler = object : SwipeToDeleteCallback(context!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = adapterMateri
                val position = viewHolder.adapterPosition
                lProgressbar.visibility = View.VISIBLE
                Handler().postDelayed({
                    val item = adapter.getData()[position]
                    item.getid?.let { presenter.deleteData(it, mApiService, position) }
                }, 300)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(rvRecyclerView)

        btnPlus.setOnClickListener {
            callback.goToAdd()
        }

        rvRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && btnPlus.isVisible)
                    btnPlus.hide()
                else if (dy < 0 && !btnPlus.isVisible)
                    btnPlus.show()
            }
        })

        return view
    }

    override fun onSuccessData(listMateri: ArrayList<Materi>) {
        if (lProgressbar.isVisible)
            lProgressbar.visibility = View.GONE
        if (listMateri.isNotEmpty()) {
            adapterMateri.addItem(listMateri)
            rvRecyclerView.scrollToPosition(listMateri.size-1)
        }
    }

    override fun berhasil(hasil: String, position: Int) {
        if (position >= 0) {
            adapterMateri.removeAt(position)
        } else {
            if (lProgressbar.isVisible)
                lProgressbar.visibility = View.GONE
            Toast.makeText(context?.applicationContext, "Berhasil $hasil"  , Toast.LENGTH_SHORT).show()
        }
    }

    override fun error() {
        if (lProgressbar.isVisible)
            lProgressbar.visibility = View.GONE
        Toast.makeText(context?.applicationContext, "ERORR" , Toast.LENGTH_SHORT).show()
    }

    override fun clickListener(materi: Materi) {
        callback.goToDetail(materi.getid, materi.nameurl)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            callback = context as Callback
        }catch (e : ClassCastException) {
            Log.d("callback", e.toString())
        }
    }

    interface Callback{
        fun goToDetail(id: String?, name: String?)
        fun goToAdd()
    }
}