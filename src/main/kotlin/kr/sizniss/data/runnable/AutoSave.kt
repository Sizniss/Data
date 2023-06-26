package kr.sizniss.data.runnable

import kr.sizniss.data.DataPlugin.Companion.plugin
import kr.sizniss.data.classes.User
import org.bukkit.scheduler.BukkitRunnable

class AutoSave : BukkitRunnable() {
    override fun run() {
        for (user in User.userList.values) {
            user.saveDataintoSql()
        }

        plugin.server.broadcastMessage("자동 저장")

        AutoSave().runTaskLaterAsynchronously(plugin, 60*20)
    }
}