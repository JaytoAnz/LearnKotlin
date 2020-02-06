package com.jayto.learnkotlin.apiservice

import com.jayto.learnkotlin.BuildConfig

class ConfigApi {
    companion object {
        private const val BASE_URL_API = BuildConfig.URL_HTTP_STAGING2
        fun getAPIService(): ApiService? {
            return RetrofitClient.getClient(BASE_URL_API)!!.create(ApiService::class.java)
        }
    }
}