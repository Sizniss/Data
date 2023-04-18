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

    fun hasUser(player: Player) : Boolean {
        return hasUser(player.uniqueId)
    }
    fun hasUser(uuid: String) : Boolean {
        return userData.keys.contains(uuid)
    }
    fun hasUser(uuid: UUID) : Boolean {
        return hasUser(uuid.toString())
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