package kr.sizniss.data

import kr.sizniss.data.classes.Sql
import kr.sizniss.data.classes.Table
import kr.sizniss.data.runnable.AutoSave
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin


class DataPlugin : JavaPlugin() {
    companion object {
        lateinit var plugin: DataPlugin
    }

    override fun onEnable() {
        plugin = this

        AutoSave().runTaskAsynchronously(plugin)

        for (table in Sql.getTableList()) {
            Table(table)
        }

    }

    override fun onDisable() {

    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(label == "test" && sender is Player) {
            sender.sendMessage("테스트 중 인 구문이 없습니다.")
            return true
        }
        return false
    }
}