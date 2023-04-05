package kr.sizniss.data.classes

import com.google.gson.JsonElement


interface Json {

    fun toJson() : JsonElement

}