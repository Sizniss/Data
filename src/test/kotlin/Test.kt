import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser

fun main(){
    val text = "[\n" +
            "\t{\n" +
            "\t\t\"dasdd\" : true,\n" +
            "\t\t\"test\" : \"sddasd\",\n" +
            "\t\t\"dasdas\": \"dasdas\",\n" +
            "\t\t\"das\" : {\n" +
            "\t\t\t\"dsad\" : true\n" +
            "\t\t}\n" +
            "\t}\n" +
            "]"
    val json = JsonParser.parseString(text) as JsonArray

    setDataAsBoolean("das.dsad",json[0] as JsonObject,true)
}

fun getDataAsBoolean(key: String,data:JsonObject): Boolean {
    val array = key.split(".") as ArrayList<String>

    var jsonObject: JsonObject = data

    val last = array[array.lastIndex]

    array.removeLast()

    for (name in array) {
        jsonObject = jsonObject.get(name).asJsonObject
    }

    return jsonObject.get(last).asBoolean
}

fun setDataAsBoolean(key: String,data:JsonObject, value: Boolean){
    val array = key.split(".") as ArrayList<String>

    var jsonObject: JsonObject = data

    val last = array[array.lastIndex]

    array.removeLast()

    for (name in array) {
        jsonObject = jsonObject.get(name).asJsonObject
    }

    jsonObject.addProperty(last, value )

    print(data)
}