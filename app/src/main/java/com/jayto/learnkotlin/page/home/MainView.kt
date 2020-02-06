package com.jayto.learnkotlin.page.home

import com.jayto.learnkotlin.apiservice.ApiService
import com.jayto.learnkotlin.model.Materi

interface MainView {
    interface Response {
        fun onSuccessData(listMateri: ArrayList<Materi>)
        fun berhasil(hasil : String, position : Int)
        fun error()
    }
    interface Presenter {
        fun tambahData(data : String, data2: String, apiService : ApiService)
        fun getAllData(apiService : ApiService)
        fun deleteData(data : String, apiService: ApiService, position : Int)
        fun updateData(data : String, data2: String, apiService : ApiService)
    }
}