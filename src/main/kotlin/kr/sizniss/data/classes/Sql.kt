package kr.sizniss.data.classes

import kr.sizniss.data.DataPlugin
import kr.sizniss.data.DataPlugin.Companion.plugin

import org.sqlite.SQLiteException
import java.sql.Connection
import java.sql.DriverManager
import java.sql.Statement

class Sql {
    companion object {
        var connection: Connection = DriverManager.getConnection("jdbc:sqlite:"+DataPlugin.plugin.dataFolder+"\\data.db")
        var statement: Statement = connection.createStatement()

        @JvmStatic fun columnList(table: String) : List<String>{
            val list = ArrayList<String>()
            val rs = statement.executeQuery("pragma table_info($table)")

            while (rs.next()){
                list.add(rs.getString("name"))
            }
            return list
        }

        @JvmStatic fun createTable(name: String) {
            statement.executeUpdate("create table if not exists $name (id char(36) unique)")
        }

        @JvmStatic fun updateData(table: String, uuid: String, column: String, value: String) {
            statement.executeUpdate("update $table set $column = \"$value\" where id = \"$uuid\"")
        }
        @JvmStatic fun addUserLine(table:String, uuid: String) {
            try {
                statement.executeUpdate("insert or ignore into $table (id) values (\"$uuid\")")
            } catch (e : SQLiteException) {
                print(e.toString())
            }
        }

        @JvmStatic fun tableList() : List<String>{
            val list = ArrayList<String>()
            val rs = statement.executeQuery("SELECT name FROM sqlite_master WHERE type='table'")

            while (rs.next()){
                list.add(rs.getString("name"))
            }
            return list
        }

        @JvmStatic fun saveData(uuid: String, data: HashMap<String, String?>){
            val dataList = HashMap<String,HashMap<String,String>>()

            for (table in tableList()){
                dataList[table] = HashMap()
            }

            for(d in data) {
                val sqlData = d.key.split(".")
                if(d.value != null)
                    dataList[sqlData[0]]!![sqlData[1]] = d.value!!
            }

            for(d in dataList) {
                var first = true

                val table = d.key
                val tableColumns = d.value

                var ids = "("
                var values = "("

                val columns = columnList(table)
                if( tableColumns.isNotEmpty()) {
                    for (dd in d.value) {
                        if (first) {
                            ids += dd.key
                            values += "\'${dd.value}\'"
                        } else {
                            ids += ", ${dd.key}"
                            values += ", '${dd.value}'"
                        }
                        first = false

                        if(!columns.contains(dd.key)){
                            statement.executeUpdate("alter table $table add column ${dd.key} varchar")
                        }
                    }
                    ids += ")"
                    values += ")"
                    statement.executeUpdate("update $table set $ids = $values where id = '$uuid'")
                }
            }
        }

        @JvmStatic fun getData(table:String, uuid:String, column: String) : String? {
            val rs = statement.executeQuery("select $column from $table where id = \"$uuid\"")
            return rs.getString(column)
        }
    }
}