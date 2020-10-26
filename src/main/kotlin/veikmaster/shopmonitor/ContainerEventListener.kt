package main.kotlin.veikmaster.shopmonitor

import org.bukkit.Location
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import java.util.*
import kotlin.collections.HashMap

class ContainerEventListener(val monitorLocations: HashMap<Location, Monitor>,
                            ): Listener {

    @EventHandler
    fun onInventoryOpenEvent(event: InventoryOpenEvent) {

    }

    @EventHandler
    fun onInventoryCloseEvent(event: InventoryCloseEvent) {

    }
}