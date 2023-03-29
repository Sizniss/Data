package kr.sizniss.data.classes

class UserProfile(val uuid: String) {

    private val data = HashMap<String,Any>()

    fun addData(key : String, value : Any) {
        data.put(key,value)
    }

    fun hasData(key: String) : Boolean{
       return data.keys.contains(key)
    }

    fun removeData(key: String) {
        data.remove(key)
    }

    fun getData(key: String) : Any? {
        return data[key]
    }

    fun getDataList() : HashMap<String,Any> {
        return data
    }

}


