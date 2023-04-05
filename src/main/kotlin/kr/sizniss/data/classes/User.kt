package kr.sizniss.data.classes

import com.google.gson.JsonElement
import com.google.gson.JsonObject

class User(val uuid: String) {

    val dataList = HashMap<String,JsonElement>()
    constructor(jsonObject: JsonObject) : this(jsonObject.get("uuid").asString) {
        for (key in jsonObject.keySet()) {
            dataList[key] = jsonObject.getAsJsonObject(key)
        }
    }
    fun getDataObject(key : String) : JsonElement? {
        return dataList[key]
    }

    fun setDataObject(key: String, jsonClass: Json) {
        dataList[key] = jsonClass.toJson()
    }
    fun setDataObject(key: String,jsonObject: JsonObject) {
        dataList[key] = jsonObject
    }

    fun removeData(key: String) {
        dataList.remove(key)
    }

}


