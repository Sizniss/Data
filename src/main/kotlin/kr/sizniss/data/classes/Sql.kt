package kr.sizniss.data.classes

import kr.sizniss.data.DataPlugin.Companion.plugin

import java.sql.Connection
import java.sql.DriverManager
import java.sql.Statement

class Sql {
    companion object {
        var connection: Connection = DriverManager.getConnection("jdbc:sqlite:"+plugin.dataFolder+"\\data.db")
        var statement: Statement = connection.createStatement()
        @JvmStatic fun getColumnList(table: String) : List<String>{
            val list = ArrayList<String>()

            val rs = statement.executeQuery("pragma table_info($table)")

            while (rs.next())
                list.add(rs.getString("name"))

            return list
        }
        @JvmStatic fun createTable(name: String) {
            statement.executeUpdate("create table $name (id char(36) unique)")
        }
        @JvmStatic fun createColumn(table: String, column: String){
            statement.executeUpdate("alter table $table add column $column varchar")
        }
        @JvmStatic fun saveData(table: String, uuid: String, column: String, value: String) {
            statement.executeUpdate("update $table set $column = \"$value\" where id = \"$uuid\"")
        }
        @JvmStatic fun addUserLine(table:String, uuid: String) {
            statement.executeUpdate("insert $table (id) values (\"$uuid\")")
        }
        @JvmStatic fun getTableList() : List<String>{
            val list = ArrayList<String>()

            val rs = statement.executeQuery("SELECT name FROM sqlite_master WHERE type='table'")

            while (rs.next())
                list.add(rs.getString("name"))

            return list
        }

    }
}