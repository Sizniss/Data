package kr.sizniss.data.managers


import com.google.gson.JsonArray
import com.google.gson.JsonObject

import kr.sizniss.data.Data.Companion.plugin
import kr.sizniss.data.classes.Config
import kr.sizniss.data.classes.UserProfile

import org.bukkit.entity.Player

import java.util.*

object UserManager {

    private val profileList = HashMap<String,UserProfile>()
    private val configFile = Config(plugin,"data.json")

    fun getUserProfile(player: Player) : UserProfile {
        return getUserProfile(player.uniqueId)
    }

    fun loadProfile() {
        for( rawProfile in configFile.getJsonArray() ) {
            val jsonProfile = rawProfile as JsonObject

            val uuid = jsonProfile.get("uuid").asString

            val jsonData = jsonProfile.getAsJsonObject("data")

            for (key in jsonData.keySet()) {
                UserProfile(uuid).addData(key,jsonData.get(key))
            }
        }
    }

    fun saveProfile() {
        val jsonArray = JsonArray()
        for (profile in profileList.values) {
            val jsonProfile = JsonObject()
            val jsonData = JsonObject()

            jsonProfile.addProperty("uuid",profile.uuid)

            for(data in profile.getDataList()) {
                if(data.value is Number)
                    jsonData.addProperty(data.key,data.value as Number)
                if(data.value is String)
                    jsonData.addProperty(data.key,data.value as String)
                if(data.value is Number)
                    jsonData.addProperty(data.key,data.value as Char)
                if(data.value is Boolean)
                    jsonData.addProperty(data.key,data.value as Boolean)
            }

            jsonProfile.add("data",jsonData)

            jsonArray.add(jsonProfile)
        }
        configFile.write(jsonArray.toString())
        configFile.save()
    }

    fun getUserProfile(uuid : UUID) : UserProfile {
        return getUserProfile(uuid.toString())
    }

    fun getUserProfile(uuid : String) : UserProfile {
        if(profileList.keys.contains(uuid)) {
            return profileList[uuid]!!
        } else {
            val profile = UserProfile(uuid)
            profileList[uuid] = profile
            return profile
        }
    }
}