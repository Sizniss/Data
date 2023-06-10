package kr.sizniss.data.classes

import org.bukkit.entity.Player
import java.util.UUID

class User(val uuid: String)  {

    constructor(player: Player) : this(player.uniqueId)
    constructor(uuid: UUID) : this(uuid.toString())

    val data : HashMap<String, String?> = HashMap()

    companion object {
        val userList = HashMap<String,User>()

        @JvmStatic fun containUser(player: Player) : Boolean {
            return containUser(player.uniqueId)
        }
        @JvmStatic fun containUser(uuid: UUID): Boolean {
            return containUser(uuid.toString())
        }
        @JvmStatic fun containUser(uuid: String): Boolean {
            return userList.keys.contains(uuid)
        }

        @JvmStatic fun getUser(player: Player) : User {
            return getUser(player.uniqueId)
        }

        @JvmStatic fun getUser(uuid: UUID) : User {
            return getUser(uuid.toString())
        }
        @JvmStatic fun getUser(uuid: String) : User {
            if(!containUser(uuid)){
              return User(uuid)
            }
            return userList[uuid]!!
        }
    }

    init {
        userList[uuid] = this
        for (table in Sql.tableList()){
            Sql.addUserLine(table, uuid)
        }
    }

    fun getDatafromSql(key: String) {
        val sqlData = key.split(".")
        data[key] = Sql.getData(sqlData[0],uuid,sqlData[1])
    }

    fun getData(key:String) : String? {
        if (!data.keys.contains(key)) {
            getDatafromSql(key)
        }
        return data[key]
    }

    fun updateData(key: String, value: String) {
        data[key] = value
    }

    fun saveDataintoSql() {
        Sql.saveData(uuid, data)
    }

}


