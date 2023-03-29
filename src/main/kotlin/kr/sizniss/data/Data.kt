package kr.sizniss.data

import org.bukkit.plugin.java.JavaPlugin

class Data : JavaPlugin() {
    companion object {
        lateinit var plugin: Data
    }

    override fun onEnable() {
        plugin = this
    }

    override fun onDisable() {

    }
}