package kr.sizniss.data.classes

import kr.sizniss.data.DataPlugin.Companion.plugin

import java.sql.Connection
import java.sql.DriverManager
import java.sql.Statement

object Sql {
    var connection: Connection = DriverManager.getConnection("jdbc:sqlite:" + plugin.dataFolder + "\\data.db")
    var statement: Statement = connection.createStatement()
    var writeMode = false
    @JvmStatic
    fun runWithOutData(sql: String) {
        if (!writeMode) {
            writeMode = true
            statement.executeUpdate(sql)
            writeMode = false
        }
    }

    @JvmStatic
    fun runToDataList(sql: String, column: String): List<String> {
        val list = ArrayList<String>()

        if (!writeMode) {
            writeMode = true
            val rs = statement.executeQuery(sql)
            writeMode = false
            while (rs.next())
                list.add(rs.getString(column))
        }

        return list
    }

    @JvmStatic
    fun runToSingleData(sql: String, column: String): String? {
        if (!writeMode) {
            writeMode = true
            val rs = statement.executeQuery(sql)
            writeMode = false
            while (rs.next())
                return rs.getString(column)
        }
        return null
    }

    @JvmStatic
    fun createTable(name: String) {
        runWithOutData("create table $name (id char(36) unique)")
    }

    @JvmStatic
    fun getTableList(): List<String> {
        return runToDataList("SELECT name FROM sqlite_master WHERE type='table'", "name")
    }

}
