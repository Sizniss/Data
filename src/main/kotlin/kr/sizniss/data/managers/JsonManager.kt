package kr.sizniss.data.managers

import com.google.gson.JsonObject
import kr.sizniss.data.Data.Companion.plugin
import org.bukkit.Location

object JsonManager {


    fun toJson(location: Location) : JsonObject {
        val jsonLocation = JsonObject()

        jsonLocation.addProperty("world",location.world!!.name)

        jsonLocation.addProperty("x",location.x)
        jsonLocation.addProperty("y",location.y)
        jsonLocation.addProperty("z",location.z)

        jsonLocation.addProperty("yaw",location.yaw)
        jsonLocation.addProperty("pitch",location.pitch)

        return jsonLocation
    }

    fun toLocation(jsonObject: JsonObject) : Location {
        val location = Location(plugin.server.getWorld(jsonObject.get("world").asString),jsonObject.get("x").asDouble,jsonObject.get("y").asDouble,jsonObject.get("z").asDouble)
        location.yaw = jsonObject.get("yaw").asFloat
        location.pitch = jsonObject.get("pitch").asFloat

        return location
    }

}