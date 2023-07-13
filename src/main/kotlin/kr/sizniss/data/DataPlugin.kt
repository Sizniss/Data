package kr.sizniss.data

import kr.sizniss.data.classes.Config
import kr.sizniss.data.classes.Sql
import kr.sizniss.data.classes.Table
import kr.sizniss.data.classes.User
import kr.sizniss.data.events.DataLoadEvent
import kr.sizniss.data.listeners.ServerListener
import kr.sizniss.data.runnable.AutoSave
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.server.PluginEnableEvent
import org.bukkit.plugin.java.JavaPlugin


class DataPlugin : JavaPlugin() {
    companion object {
        lateinit var plugin: DataPlugin
        lateinit var jsonConfig: Config
    }

    override fun onEnable() {
        ServerListener()

        jsonConfig = Config(plugin,"config.json")

        AutoSave().runTaskAsynchronously(plugin)

        if(!plugin.dataFolder.exists()){
            plugin.dataFolder.mkdir()
        }

        for(table in Sql.getTableList()){
            Table.loadTable(table)
        }
    }

    override fun onLoad() {
        plugin = this

        server.broadcastMessage("로딩됨")

        for(table in Sql.getTableList()){
            Table.loadTable(table)
        }

        for(player in server.onlinePlayers){
            User.loadUser(player)
        }

    }

    override fun onDisable() {
        User.saveAllDataintoSql()
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(label == "test" && sender is Player && sender.isOp) {
            sender.sendMessage("테스트 중 인 구문이 없습니다.")
            return true
        }
        return false
    }

    @EventHandler
    fun onLoadComplete(event: PluginEnableEvent){
        if(event.plugin == this){
            server.pluginManager.callEvent(DataLoadEvent(this))
            server.broadcastMessage("ㄴㄴㄴㄴㄴㄴ")
        }
    }
}