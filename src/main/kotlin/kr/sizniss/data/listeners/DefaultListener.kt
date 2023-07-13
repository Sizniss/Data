package kr.sizniss.data.listeners

import kr.sizniss.data.DataPlugin
import org.bukkit.event.Listener

abstract class DefaultListener : Listener {

    init {
        this.register()
    }

    private var registered : Boolean = false


    private fun register() {
        if( !registered ) {
            DataPlugin.plugin.server.pluginManager.registerEvents(this, DataPlugin.plugin)
            registered = true
        }
    }

}