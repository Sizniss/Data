package kr.sizniss.data.classes

import com.google.gson.*
import kr.sizniss.data.DataPlugin
import org.bukkit.Location
import org.bukkit.inventory.ItemStack


class Util {
    companion object {
        @JvmStatic fun toJson(string: String) : JsonObject {
            return Gson().fromJson(string, JsonObject::class.java)
        }

        @JvmStatic fun toJson(itemStack: ItemStack) : String {
            val jsonItem = JsonObject()

            for (data in itemStack.serialize()) {
                jsonItem.addProperty(data.key,data.value.toString())
            }
            return jsonItem.toString()
        }

        @JvmStatic fun toJson(location: Location) : String {
            val jsonLocation = JsonObject()

            jsonLocation.addProperty("world",location.world!!.name)

            jsonLocation.addProperty("x",location.x)
            jsonLocation.addProperty("y",location.y)
            jsonLocation.addProperty("z",location.z)

            jsonLocation.addProperty("yaw",location.yaw)
            jsonLocation.addProperty("pitch",location.pitch)

            return jsonLocation.toString()
        }

        @JvmStatic fun toLocation(jsonString: String) : Location? {
            if (jsonString.isEmpty())
                return null
            val jsonObject = toJson(jsonString)

            val location = Location(DataPlugin.plugin.server.getWorld(jsonObject.get("world").asString),jsonObject.get("x").asDouble,jsonObject.get("y").asDouble,jsonObject.get("z").asDouble)
            location.yaw = jsonObject.get("yaw").asFloat
            location.pitch = jsonObject.get("pitch").asFloat

            return location
        }

        @JvmStatic fun toPrettyJson(jsonElement: JsonElement): String {
            return GsonBuilder().setPrettyPrinting().create().toJson(jsonElement)
        }

    }
}