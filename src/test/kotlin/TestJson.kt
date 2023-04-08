import com.google.gson.JsonElement
import com.google.gson.JsonObject
import kr.sizniss.data.classes.Json

class TestJson(val test:Int) : Json {


    override fun toJson(): JsonElement {
        val jsonObject = JsonObject()
        jsonObject.addProperty("test",123124)
        return jsonObject
    }

}