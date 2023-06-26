package kr.sizniss.data.classes

import org.bukkit.entity.Player
import java.util.UUID

class User(val uuid: String)  {
    constructor(player: Player) : this(player.uniqueId)
    constructor(uuid: UUID) : this(uuid.toString())

    companion object {
        val userList = HashMap<String,User>()
        @JvmStatic fun getKey(table: Table, column: String) : String{
            return "${table.name}.$column"
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
    }

    private val data = HashMap<String, String>()

    fun getData(table: Table, column: String) : String? {
        val key = getKey(table,column)
        if(getKey(table,column) in data.keys) {
            return data[key]
        } else  {
            if(table.containColumn(column)){
                return data[getKey(table,column)]
            }
        }
        return null
    }
    fun updateData(table: Table, column:String, value: String) {
        if(!table.containColumn(column))
            table.createColumn(column)
        data[getKey(table,column)] = value
    }
    fun saveDataintoSql() {
        val dataList = HashMap<Table, HashMap<String, String>>() // Table, Data<Key, Value>

        for (table in Table.getTableList()) {
            dataList[table] = HashMap()
        }
        for ((key, value) in data) {
            val keyData = key.split(".")
            val table = Table.getTable(keyData[0])
            val columnName = keyData[1]

            dataList[table]!![columnName] = value
        }

        for(table in dataList.keys) {
            table.saveData(uuid, dataList[table]!!.toMap())
        }

    }

}


