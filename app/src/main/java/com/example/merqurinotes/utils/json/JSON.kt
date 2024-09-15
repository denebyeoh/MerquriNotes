package com.example.merqurinotes.utils.json

import com.google.gson.Gson
import java.lang.reflect.Type

object JSON {
    fun toJSONString(`object`: Any?): String {
        val gson = Gson()
        return gson.toJson(`object`)
    }

    fun <T> toObject(text: String, typeOfT: Type): T {
        val gson = Gson()
        return gson.fromJson(text, typeOfT)
    }
}