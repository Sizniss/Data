package kr.sizniss.data.managers

import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import kr.sizniss.data.DataPlugin.Companion.plugin
import org.bukkit.Location
import org.bukkit.inventory.ItemStack

object JsonManager {

    fun toJson(itemStack: ItemStack) : JsonObject {
        val jsonItem = JsonObject()

        for (data in itemStack.serialize()) {
            jsonItem.addProperty(data.key,data.value.toString())
        }
        return jsonItem
    }

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

    fun toPrettyJson(jsonElement: JsonElement): String {
        return GsonBuilder().setPrettyPrinting().create().toJson(jsonElement)
    }

}