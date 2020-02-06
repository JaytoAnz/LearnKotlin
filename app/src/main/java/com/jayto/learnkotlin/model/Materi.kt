package com.jayto.learnkotlin.model

import org.json.JSONObject

class Materi(tv: JSONObject) {
    val getid: String? = tv.getString("getid")
    val nameurl: String? = tv.getString("nameurl")
    val video: String? = tv.getString("video")
    val nameurl2: String? = tv.getString("nameurl2")
    val video2: String? = tv.getString("video2")
    val linkkuis: String? = tv.getString("linkkuis")
    val linknilai: String? = tv.getString("linknilai")
    val date: String? = tv.getString("date")
}