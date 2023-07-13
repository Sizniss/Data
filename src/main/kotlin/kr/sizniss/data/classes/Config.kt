package kr.sizniss.data.classes

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import org.bukkit.plugin.java.JavaPlugin
import java.io.File


class Config(private val plugin:JavaPlugin, filename:String) : File(plugin.dataFolder,filename){

    private var text = "{}"

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
            write("{\"saveTick\" : 1200,\"savingMessage\" : \"%s개의 데이터 자동 저장중...\",\"saveMessage\" : \"자동 저장 %sms\"}")
            save()
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

    fun getJsonObject(): JsonObject{
        read()
        return JsonParser.parseString(text) as JsonObject
    }

}