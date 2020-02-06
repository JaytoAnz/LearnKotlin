package com.jayto.learnkotlin.page.beranda

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.jayto.learnkotlin.page.home.MainActivity
import com.jayto.learnkotlin.page.home.MainPresenter
import com.jayto.learnkotlin.page.home.MainView
import com.jayto.learnkotlin.R
import com.jayto.learnkotlin.apiservice.ApiService
import com.jayto.learnkotlin.apiservice.ConfigApi
import com.jayto.learnkotlin.model.Materi
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_detail.*
import java.util.concurrent.TimeUnit

class DetaillActivity : AppCompatActivity(), MainView.Response {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val mApiService : ApiService = ConfigApi.getAPIService()!!
        val presenter = MainPresenter(this)

        Completable
                .timer(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    init()
                }, {
                    Log.d("detaillactivity", it.message)
                })


        btnUpdate.setOnClickListener {
            if (!llProgressBar.isVisible)
                llProgressBar.visibility = View.VISIBLE
            presenter.updateData(idName.text.toString(), Name.text.toString(), mApiService)
        }
    }

    private fun init(){
        if (llProgressBar.isVisible)
            llProgressBar.visibility = View.GONE
        val bundle = intent.extras
        if (bundle != null) {
            val name  = bundle.getString("EXTRA")
            val user  = bundle.getString("EXTRANAME")
            idName.setText("$name")
            Name.setText("$user")
        }
    }

    override fun onSuccessData(listMateri: ArrayList<Materi>) {
    }

    override fun berhasil(hasil: String, position: Int) {
        if (position<0){
            if (llProgressBar.isVisible)
                llProgressBar.visibility = View.GONE
            Toast.makeText(applicationContext, "UPDATE $hasil"  , Toast.LENGTH_SHORT).show()
            val intent = Intent(this@DetaillActivity, MainActivity::class.java)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    override fun error() {
        if (llProgressBar.isVisible)
            llProgressBar.visibility = View.GONE
        Toast.makeText(applicationContext, "ERORR" , Toast.LENGTH_SHORT).show()
    }
}