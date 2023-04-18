package kr.sizniss.data

import kr.sizniss.data.classes.Config
import org.bukkit.plugin.java.JavaPlugin

class DataPlugin : JavaPlugin() {
    companion object {
        lateinit var plugin: DataPlugin

    }

    override fun onEnable() {
        plugin = this
        Data.configFile = Config(plugin.dataFolder,"save.json")
    }

    override fun onDisable() {
        Data.saveJson()
    }
}