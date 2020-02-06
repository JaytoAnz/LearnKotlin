package com.jayto.learnkotlin.page.home

import android.util.Log
import com.jayto.learnkotlin.apiservice.ApiService
import com.jayto.learnkotlin.model.Materi
import com.jayto.learnkotlin.model.ResponseGet
import com.jayto.learnkotlin.model.ResponsePost
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class MainPresenter (mainView: MainView.Response) : MainView.Presenter {

    var view : MainView.Response? = null
    var list : ArrayList<Materi> = ArrayList()
    lateinit var messageApi : String

    init {
        view = mainView
    }

    override fun tambahData(data: String, data2 : String, apiService: ApiService) {
        apiService.post(data, data2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResponsePost?> {
                    override fun onSubscribe(d: Disposable) {}

                    override fun onError(error: Throwable) {
                        Log.d("RETROFITT: ", error.toString())
                    }

                    override fun onNext(t: ResponsePost) {
                        messageApi = t.message!!
                    }

                    override fun onComplete() {
                        view?.berhasil(messageApi, -1)
                    }

                })
    }

    override fun getAllData(apiService: ApiService) {
        apiService.getAll()
                .delay(300, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResponseGet?> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(t: ResponseGet) {
                        t.materiList?.let {
                            list.clear()
                            list.addAll(it)
                        }
                    }

                    override fun onError(e: Throwable) {
                        view?.error()
                    }

                    override fun onComplete() {
                        view?.onSuccessData(list)
                    }
                })
    }

    override fun deleteData(data: String, apiService: ApiService, position:Int) {
        apiService.delete(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResponsePost?> {
                    override fun onSubscribe(d: Disposable) {}

                    override fun onError(error: Throwable) {
                        Log.d("RETROFITT: ", error.toString())
                    }

                    override fun onNext(t: ResponsePost) {
                        view?.berhasil(t.message!!,position)
                    }

                    override fun onComplete() {
                        getAllData(apiService)
                    }
                })
    }

    override fun updateData(data: String, data2: String, apiService: ApiService) {
        apiService.update(data, data2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResponsePost?> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(t: ResponsePost) {
                        messageApi = t.message!!
                    }

                    override fun onComplete() {
                        view?.berhasil(messageApi,-1)
                    }

                    override fun onError(e: Throwable) {
                        view?.error()
                    }
                })
    }
}