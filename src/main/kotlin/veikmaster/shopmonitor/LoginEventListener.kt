package main.kotlin.veikmaster.shopmonitor

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import java.util.*
import kotlin.collections.HashMap

class LoginEventListener(val monitors: HashMap<UUID, MutableList<Monitor>>,
): Listener {

    @EventHandler
    fun onPlayerJoinEvent(event: PlayerJoinEvent) {
        val player = event.player
        if (monitors[player.uniqueId].isNullOrEmpty()) return
        if (monitors[player.uniqueId]!!.any {it.unread}) Messager.sendLoginMessage(player)
    }
}