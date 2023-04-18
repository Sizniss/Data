package kr.sizniss.data

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kr.sizniss.data.classes.Config
import kr.sizniss.data.classes.User
import kr.sizniss.data.managers.UserManager

object Data {

    lateinit var configFile : Config


    fun saveJson() {
        val jsonArray = JsonArray()
        for (user in UserManager.userData.values) {
            val userObject = JsonObject()

            userObject.addProperty("uuid", user.uuid)
            userObject.add("data", user.dataObject)

            jsonArray.add(userObject)
        }
        configFile.write(jsonArray.toString())
        configFile.save()
    }

    fun loadJson() {
        for (userData in configFile.getJsonArray()) {
            val userJson = userData as JsonObject

            User(userJson.get("uuid").asString,userJson.get("data").asJsonObject)
        }
    }

}