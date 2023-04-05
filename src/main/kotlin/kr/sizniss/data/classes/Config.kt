package kr.sizniss.data.classes

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser

import java.io.File


class Config(private val path:File, filename:String) {

    private var file = File(path,filename)
    private var text = "[]"

    init {
        makeDataDir()
        create()
        text = read()
    }

    private fun makeDataDir() {
        if (!file.exists()) {
            path.mkdir()
        }
    }

    private fun create() {
        if (!file.exists()) {
            file.createNewFile()
            write("[]")
        }
    }

    private fun read() : String {
        return file.readText()
    }
    fun write(jsonObject: JsonObject) {
        write(jsonObject.asString)
    }
    fun write(jsonArray: JsonArray) {
        write(jsonArray.asString)
    }
    fun write(text:String) {
       this.text = text
    }

    fun save() {
        file.writeText(text)
    }

    fun getJsonArray(): JsonArray{
        read()
        return JsonParser.parseString(text) as JsonArray
    }

}