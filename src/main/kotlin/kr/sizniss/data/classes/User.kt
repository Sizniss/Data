package kr.sizniss.data.classes

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import kr.sizniss.data.Data

class User(val uuid: String) {

    val dataList = HashMap<String,Json>()
    constructor(jsonObject: JsonObject) : this(jsonObject.get("uuid").asString) {
        for (key in jsonObject.keySet()) {
            dataList[key] = Data.jsonMap[key]!!.getDeclaredConstructor().newInstance(jsonObject[key]) as Json
        }
    }
    fun getDataObject(key : String) : Json? {
        return dataList[key]
    }

    fun setDataObject(key: String, jsonClass: Json) {
        dataList[key] = jsonClass
    }

    fun removeData(key: String) {
        dataList.remove(key)
    }

}


