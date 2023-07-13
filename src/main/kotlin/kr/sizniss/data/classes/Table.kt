package kr.sizniss.data.classes

import kr.sizniss.data.DataPlugin.Companion.plugin
import kotlin.collections.HashMap
import kotlin.collections.HashSet

class Table(val name:String) {
    companion object {
        private val tableList = HashMap<String, Table>()

        @JvmStatic
        fun loadTable(name: String) {
            tableList[name] = Table(name)
            plugin.server.broadcastMessage("SqlList " + Sql.getTableList().toString())
            if(name !in Sql.getTableList()){
                Sql.createTable(name)
            }
            plugin.server.broadcastMessage("tableList "+tableList.keys.toString())
        }
        @JvmStatic
        fun containTable(name: String): Boolean {
            return tableList.keys.contains(name)
        }
        @JvmStatic
        fun getTable(name: String): Table? {
            return tableList[name]
        }
        @JvmStatic
        fun getTableNameList() : List<String> {
            return tableList.keys.toList()
        }

        @JvmStatic
        fun getTableList() : List<Table> {
            return  tableList.values.toList()
        }
    }

    private val columns = HashSet<String>()
    private val availableIdList = HashSet<String>()

    init {
        for(column in getSqlColumnList()){
            columns.add(column)
        }
        for (uuid in getData("id")){
            availableIdList.add(uuid)
        }
    }

    fun getSqlColumnList() : List<String> {
        return Sql.runToDataList("pragma table_info($name)","name")
    }
    fun getSqlAvailableIDList() : List<String>{
        return getData("id")
    }
    fun addUserLine(uuid: String) {
        availableIdList.add(uuid)
        Sql.runWithOutData("insert into $name (id) values (\"$uuid\")")
    }
    fun containColumn(column: String) : Boolean {
        return column in columns
    }
    fun createColumn(column: String) {
        columns.add(column)
        Sql.runWithOutData("alter table $name add column $column varchar")
    }
    fun getData(column: String) : List<String> {
        if(column in columns)
            return Sql.runToDataList("select $column from $name", column)
        error("Not Find column:$column")
    }
    fun getData(column: String, uuid:String) : String? {
        if(column in columns && uuid in availableIdList){
            return Sql.runToSingleData("select $column from $name where id = \"$uuid\"", column)
        }
        return null
    }
    fun saveData(uuid: String, data: Map<String, String>){
        if(data.isNotEmpty()) {
            if(uuid !in availableIdList){
                addUserLine(uuid)
            }
            var first = true
            var ids = "("
            var values = "("
            for ((key, value) in data) {
                if(key !in columns){
                    createColumn(key)
                }
                if (first) {
                    ids += key
                    values += "\'$value\'"
                } else {
                    ids += ", $key"
                    values += ", '$value'"
                }
                first = false
            }
            ids += ")"
            values += ")"
            Sql.runWithOutData("update $name set $ids = $values where id = '$uuid'")
        }
    }
}