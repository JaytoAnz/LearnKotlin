package com.jayto.learnkotlin.model

import com.google.gson.annotations.SerializedName

class ResponseGet {

    @SerializedName("materi")
    val materiList: List<Materi>? = null
    val last_update: String? = null

}