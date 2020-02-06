package com.jayto.learnkotlin.learncoroutines

import android.app.ProgressDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jayto.learnkotlin.R
import com.jayto.learnkotlin.model.Users
import kotlinx.android.synthetic.main.activity_view.*
import kotlinx.coroutines.*

class ViewActivity : AppCompatActivity() {

    lateinit var viewAdapter: ViewAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var layoutManager: LinearLayoutManager
    var users: MutableList<Users> = mutableListOf()
    private lateinit var progressDialog : ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)

        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("loading...")
        progressDialog.show()

//        CoroutineScope(Dispatchers.IO).launch {
//            fakeApiRequest()
//        }

        fakeApiRequest()
    }

    private fun AdapterUsers() {
        progressDialog.dismiss()
        recyclerView = findViewById(R.id.rv_tv)
        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        viewAdapter = ViewAdapter(users)
        recyclerView.adapter = viewAdapter
    }

    private fun setAdapter(list: MutableList<Users>){
//        withContext(Dispatchers.Main){
            users.clear()
            users.addAll(list)
            AdapterUsers()
//        }
    }

    private fun fakeApiRequest(){
        logThread("fakeApiRequest")
        val result1 = getResult1FromApi()
        setAdapter(result1)
    }

    private fun getResult1FromApi(): MutableList<Users> {
        val users : MutableList<Users> = mutableListOf()

        for ((item) in (1 ..10000).withIndex()){
            users.add(Users(item, "Jayto $item"))
            logThread("getResult1FromApi $item")
        }

//        delay(1000)
        return users
    }

    private fun logThread(methodName: String){
        println("debug: ${methodName}: ${Thread.currentThread().name}")
    }
}