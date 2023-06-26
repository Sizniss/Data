package kr.sizniss.data.classes

import kotlin.collections.HashMap
import kotlin.collections.HashSet

class Table(val name:String) {
    companion object {
        private val availableIdList = HashSet<String>()
        private val tableList = HashMap<String, Table>()
        @JvmStatic
        fun containTable(name: String): Boolean {
            return tableList.keys.contains(name)
        }
        @JvmStatic
        fun getTable(name: String): Table {
            if (!containTable(name)) {
                return Table(name)
            }
            return tableList[name]!!
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
    init {
        tableList[name] = this

        if(name !in Sql.getTableList()){
            Sql.createTable(name)
        }
        for(column in Sql.getColumnList(name)){
            columns.add(column)
        }
        for (uuid in getData("id")){
            availableIdList.add(uuid)
        }
    }
    fun containColumn(column: String) : Boolean {
        return column in columns
    }
    fun createColumn(column: String) {
        Sql.createColumn(this, column)
    }
    fun getData(column: String) : List<String> {
        val list = HashSet<String>()
        if(containColumn(column)){
            val rs = Sql.statement.executeQuery("select $column from $name")
            while (rs.next())
                list.add(rs.getString(column))
        }
        return list.toList()
    }
    fun getData(column: String, uuid:String) : String? {
        if(containColumn(column)){
            val rs = Sql.statement.executeQuery("select $column from $name where id = \"$uuid\"")
            if (rs.next())
                return rs.getString(column)
        }
        return null
    }
    fun saveData(uuid: String, data: Map<String, String>){
        if(data.isNotEmpty()) {
            var first = true
            var ids = "("
            var values = "("
            for ((key, value) in data) {
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
            Sql.statement.executeUpdate("update $name set $ids = $values where id = '$uuid'")
        }
    }
}