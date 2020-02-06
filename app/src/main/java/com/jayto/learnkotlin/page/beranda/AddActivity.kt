package com.jayto.learnkotlin.page.beranda

import android.app.Activity
import android.content.Intent
import android.os.Bundle
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
import kotlinx.android.synthetic.main.activity_add.*

class AddActivity : AppCompatActivity(), MainView.Response{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        val mApiService : ApiService = ConfigApi.getAPIService()!!
        val presenter = MainPresenter(this)

        btn_add.setOnClickListener {
            if (!llProgressBar.isVisible)
                llProgressBar.visibility = View.VISIBLE
            if (checkInput()) {
                presenter.tambahData(edit_name.text.toString(), edit_jalan.text.toString(), mApiService)
            } else {
                if (llProgressBar.isVisible)
                    llProgressBar.visibility = View.GONE
                Toast.makeText(applicationContext, "form belum terisi", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkInput() : Boolean {
        val editName = edit_name.text.toString()
        val editJalan = edit_jalan.text.toString()
        if (editName.isNotEmpty() && editJalan.isNotEmpty())
            return true

        return false
    }

    override fun onSuccessData(listMateri: ArrayList<Materi>) {

    }

    override fun berhasil(hasil: String, position: Int) {
        if (position < 0) {
            if (llProgressBar.isVisible)
                llProgressBar.visibility = View.GONE
            Toast.makeText(applicationContext, "Berhasil $hasil"  , Toast.LENGTH_SHORT).show()
            Intent(applicationContext, MainActivity::class.java).apply {
                putExtra("BERANDA", true)
            }
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