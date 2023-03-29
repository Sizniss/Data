package kr.sizniss.data.classes

import com.google.gson.JsonArray
import com.google.gson.JsonParser
import org.bukkit.plugin.java.JavaPlugin
import java.io.File


class Config(private val plugin:JavaPlugin, filename:String) : File(plugin.dataFolder,filename){

    var text = "[]"

    init {
        makeDataDir()
        create()
        text = read()
    }

    private fun makeDataDir() {
        if (!plugin.dataFolder.exists()) {
            plugin.dataFolder.mkdir()
        }
    }

    private fun create() {
        if (!this.exists()) {
            this.createNewFile()
            write("[]")
        }
    }

    private fun read() : String {
        return readText()
    }

    fun write(text:String) {
       this.text = text
    }

    fun save() {
        this.writeText(text)
    }

    fun getJsonArray(): JsonArray{
        read()
        return JsonParser.parseString(text) as JsonArray
    }

}