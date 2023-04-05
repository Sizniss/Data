package kr.sizniss.data.managers


import com.google.gson.JsonObject

import kr.sizniss.data.classes.User

import org.bukkit.entity.Player

import java.util.*

object UserManager {

    val userData = HashMap<String, User>()

    fun updateUser(user: User) {
        userData[user.uuid] = user
    }

    fun updateUser(jsonObject: JsonObject) {
        val user = User(jsonObject)
        userData[user.uuid] = user
    }

    fun getUser(player: Player) : User {
        return getUser(player.uniqueId)
    }

    fun getUser(uuid : UUID) : User {
        return getUser(uuid.toString())
    }

    fun getUser(uuid : String) : User {
        if(userData.keys.contains(uuid)) {
            return userData[uuid]!!
        } else {
            val user = User(uuid)
            updateUser(user)
            return user
        }
    }
}