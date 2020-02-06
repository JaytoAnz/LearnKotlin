package com.jayto.learnkotlin.apiservice

import com.jayto.learnkotlin.model.ResponsePost
import com.jayto.learnkotlin.model.ResponseGet
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("ViewLearn.php")
    fun getAll(): Observable<ResponseGet>

    @FormUrlEncoded
    @POST("saveMateri.php")
    fun post(@Field("nameUrl") title: String?,
                   @Field("video") body: String?): Observable<ResponsePost>

    @FormUrlEncoded
    @POST("updateMateri.php")
    fun update(@Field("no_id") title: String?,
               @Field("nameUrl") body: String?): Observable<ResponsePost>

    @FormUrlEncoded
    @POST("deleteMateri.php")
    fun delete(@Field("no_id") title: String?): Observable<ResponsePost>
}