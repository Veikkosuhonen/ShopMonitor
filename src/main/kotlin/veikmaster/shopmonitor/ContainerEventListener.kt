package main.kotlin.veikmaster.shopmonitor

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent

class ContainerEventListener(val monitorLocations: HashMap<Location, Monitor>,
                             val installer: Installer): Listener {

    @EventHandler
    fun onInventoryOpenEvent(event: InventoryOpenEvent) {
        event.inventory.location?.let { installer.install(it, event.player.uniqueId) }

        val monitor = monitorLocations[event.inventory.location] ?: return
        monitor.contents.clear()
        event.view.topInventory.contents.filterNotNull().forEach {
            monitor.contents.add(Transaction(it.type, it.amount))
        }
    }

    @EventHandler
    fun onInventoryCloseEvent(event: InventoryCloseEvent) {
        val monitor = monitorLocations[event.inventory.location] ?: return

        val changes = HashMap<Material, Int>()
        event.view.topInventory.contents.filterNotNull().forEach {
            changes[it.type] = it.amount + (changes[it.type] ?: 0)
        }
        monitor.contents.forEach {
            changes[it.material] = - it.amount + (changes[it.material] ?: 0)
        }
        changes.forEach { (material, amount) ->
            if (amount != 0) {
                monitor.transactions.add(Transaction(material, amount))
                monitor.unread = true
            }
        }
    }
}