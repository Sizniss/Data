package kr.sizniss.data.classes

import kr.sizniss.data.DataPlugin.Companion.plugin
import org.bukkit.entity.Player
import java.util.UUID

open class User(val uuid: String)  {
    constructor(player: Player) : this(player.uniqueId)
    constructor(uuid: UUID) : this(uuid.toString())

    companion object {
        val userList = HashMap<String,User>()

        @JvmStatic fun loadUser(player: Player) {
            val uuid = player.uniqueId.toString()
            userList[uuid] = User(uuid)
        }
        @JvmStatic fun containUser(player: Player) : Boolean {
            return containUser(player.uniqueId)
        }
        @JvmStatic fun containUser(uuid: UUID): Boolean {
            return containUser(uuid.toString())
        }
        @JvmStatic fun containUser(uuid: String): Boolean {
            return userList.keys.contains(uuid)
        }
        @JvmStatic fun getUser(player: Player) : User? {
            return getUser(player.uniqueId)
        }
        @JvmStatic fun getUser(uuid: UUID) : User? {
            return getUser(uuid.toString())
        }
        @JvmStatic fun getUser(uuid: String) : User? {
            return userList[uuid]
        }

        @JvmStatic fun saveAllDataintoSql() {
            for (user in userList.values) {
                user.saveDataintoSql()
            }
        }
    }

    private val userData = HashMap<String, String>()

    fun unload() {
        userList.remove(uuid)
        saveDataintoSql()
    }
    fun containData(key: String) : Boolean{
        return key in userList.keys
    }
    fun getData(key: String) : String? {
        if(containData(key)){
            return userData[key]
        } else {
            return getDatafromSql(key)
        }
    }
    fun updateData(key: String, value: String) {
        userData[key] = value
    }

    fun getDatafromSql(key: String) : String? {
        val tableName = key.split(".")[0]
        val column = key.split(".")[1]
        if(Table.containTable(tableName)) {
            val table = Table.getTable(tableName)
            if (table!!.containColumn(column)) {
                val data = table.getData(column,uuid)
                if(data !=null){
                    userData[key] = data
                }
                return data
            }
        } else {
            error("Table Not Found : $tableName")
        }
        return null
    }
    fun saveDataintoSql() {
        val saveDataList = HashMap<String, HashMap<String, String>>() // Table, Data<Column, Value>

        for (table in Table.getTableNameList()) {
            saveDataList[table] = HashMap()
        }
        for ((key, value) in userData ) { //userData<String,Value>
            val tableName = key.split(".")[0]
            val column = key.split(".")[1]
            if(tableName in Table.getTableNameList())
                saveDataList[tableName]!![column] = value
        }

        for(tableName in saveDataList.keys) {
            val table = Table.getTable(tableName)!!
            plugin.server.broadcastMessage("$uuid : $tableName ${saveDataList[tableName]!!.toMap()}")
            table.saveData(uuid, saveDataList[tableName]!!.toMap())
        }

    }

}


