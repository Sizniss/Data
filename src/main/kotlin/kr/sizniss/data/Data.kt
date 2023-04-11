package kr.sizniss.data

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kr.sizniss.data.classes.Config
import kr.sizniss.data.classes.Json
import kr.sizniss.data.classes.User
import kr.sizniss.data.managers.UserManager
import org.bukkit.plugin.java.JavaPlugin

class Data : JavaPlugin() {
    companion object {
        lateinit var plugin: Data

        val jsonMap = HashMap<String,Class<out Json>>()

        lateinit var configFile : Config

    }


    fun saveJson() {
        val jsonArray = JsonArray()
        for (user in UserManager.userData.values) {
            val userObject = JsonObject()

            userObject.addProperty("uuid", user.uuid)

            val dataObject = JsonObject()

            for (data in user.dataList) {
                dataObject.add(data.key, data.value.toJson())
            }

            userObject.add("data", dataObject)

            jsonArray.add(userObject)
        }
        configFile.write(jsonArray.toString())
        configFile.save()
    }

    fun loadJson(key:String) {
        for (userData in configFile.getJsonArray()) {
            val userJson = userData as JsonObject

            val user = User(userJson.get("uuid").asString)

            val dataJson = userJson.getAsJsonObject("data")

            if(dataJson.keySet().contains(key)) {
                user.setDataObject(
                    key,
                    jsonMap[key]!!.getDeclaredConstructor().newInstance(dataJson.getAsJsonObject(key)) as Json
                )
            }
        }
    }

    fun loadAllJson() {
        for (key in jsonMap.keys) {
            loadJson(key)
        }
    }

    fun <T:Json> request(key:String, jsonClass: Class<T>) {
        jsonMap[key] = jsonClass
        loadJson(key)
    }

    override fun onEnable() {
        plugin = this
        configFile = Config(plugin.dataFolder, "data.json")
    }

    override fun onDisable() {
        saveJson()
    }
}