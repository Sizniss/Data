package kr.sizniss.data.runnable

import kr.sizniss.data.DataPlugin.Companion.jsonConfig

import kr.sizniss.data.DataPlugin.Companion.plugin
import kr.sizniss.data.classes.User
import org.bukkit.scheduler.BukkitRunnable

class AutoSave : BukkitRunnable() {
    override fun run() {
        val startTime = System.currentTimeMillis()
        val data = User.userList
        val jsonObject = jsonConfig.getJsonObject()
        plugin.server.broadcastMessage(jsonObject.get("savingMessage").asString.format(data.size))

        User.saveAllDataintoSql()

        val endTime = System.currentTimeMillis()
        plugin.server.broadcastMessage(jsonObject.get("saveMessage").asString.format(endTime-startTime))

        AutoSave().runTaskLaterAsynchronously(plugin, jsonObject.get("saveTick").asLong)
    }
}