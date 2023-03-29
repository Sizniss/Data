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

            for (key in jsonProfile.keySet()) {
                if(key != "uuid") {
                    UserProfile(uuid).addData(key,jsonProfile.get(key))
                }
            }
        }
    }

    fun saveProfile() {
        val jsonArray = JsonArray()
        for (profile in profileList.values) {
            val jsonProfile = JsonObject()
            jsonProfile.addProperty("uuid",profile.uuid)
            for(data in profile.getDataList()) {

            }
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