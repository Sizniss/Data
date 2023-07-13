package kr.sizniss.data.listeners

import kr.sizniss.data.classes.User
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class ServerListener : DefaultListener() {
    @EventHandler
    fun onJoinUser(event: PlayerJoinEvent) {
        User.loadUser(event.player)
    }

    @EventHandler
    fun onLeaveUser(event: PlayerQuitEvent){
        if(User.containUser(event.player)){
            User.getUser(event.player)!!.unload()
        }
    }
}