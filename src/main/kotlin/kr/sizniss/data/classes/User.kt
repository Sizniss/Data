package kr.sizniss.data.classes

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import kr.sizniss.data.managers.JsonManager
import kr.sizniss.data.managers.UserManager
import org.bukkit.Location
import org.bukkit.entity.Player
import java.util.UUID

class User(val uuid: String)  {

    var dataObject: JsonObject

    constructor(uuid : String, jsonObject: JsonObject) : this(uuid) {
        dataObject = jsonObject
    }

    constructor(player: Player) : this(player.uniqueId)

    constructor(uuid: UUID) : this(uuid.toString())

    init {
        if( UserManager.hasUser(uuid)){
            dataObject = UserManager.getUser(uuid).dataObject
        } else {
            dataObject = JsonObject()
        }

        UserManager.updateUser(this)
    }

    fun getDataAsString(key: String) : String? {
        if(getData(key) == null) {
            return null
        } else {
            return getData(key)!!.asString
        }
    }
    fun getDataAsFloat(key: String) : Float? {
        if(getData(key) == null) {
            return null
        } else {
            return getData(key)!!.asFloat
        }
    }
    fun getDataAsDouble(key: String) : Double? {
        if(getData(key) == null) {
            return null
        } else {
            return getData(key)!!.asDouble
        }
    }

    fun getDataAsBoolean(key: String) : Boolean? {
        if(getData(key) == null) {
            return null
        } else {
            return getData(key)!!.asBoolean
        }
    }

    fun getDataAsLocation(key: String) : Location? {
        if(getData(key) == null) {
            return null
        } else {
            return JsonManager.toLocation(getData(key) as JsonObject)
        }
    }

    fun getDataLastIndex(keyList: ArrayList<String>) : JsonObject {
        var tempObject : JsonObject = dataObject

        for (name in keyList) {
            tempObject = tempObject.get(name).asJsonObject
        }

        return tempObject
    }

    fun setDataLastIndex(keyList: ArrayList<String>) : JsonObject {
        var tempObject : JsonObject = dataObject

        for (name in keyList) {
            if (!tempObject.keySet().contains(name)){
                val newObject = JsonObject()
                tempObject.add(name,newObject)
            }
            tempObject = tempObject.get(name).asJsonObject
        }
        return tempObject
    }
    fun getData(key:String) : JsonElement? {
        val array = key.split(".") as ArrayList<String>
        val last = array[array.lastIndex]
        array.removeLast()
        return getDataLastIndex(array).get(last)
    }

    fun setData(key: String, data: JsonElement) {
        val array = key.split(".") as ArrayList<String>
        val last = array[array.lastIndex]
        array.removeLast()
        setDataLastIndex(array).add(last,data)
    }


}


