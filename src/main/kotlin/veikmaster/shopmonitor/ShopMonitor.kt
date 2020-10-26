package main.kotlin.veikmaster.shopmonitor

import main.kotlin.veikmaster.shopmonitor.dao.FakeDAO
import org.bukkit.Location
import org.bukkit.plugin.java.JavaPlugin
import java.util.*
import kotlin.collections.HashMap

class ShopMonitor: JavaPlugin() {

    val dao = FakeDAO()
    lateinit var monitors: HashMap<UUID, MutableList<Monitor>>
    lateinit var monitorLocations: HashMap<Location, Monitor>

    override fun onEnable() {
        getData()
        val eventListener = ContainerEventListener(monitorLocations)
        this.server.pluginManager.registerEvents(eventListener, this)
    }

    override fun onDisable() {
        saveData()
    }

    fun getData() {
        monitors = dao.monitors
        monitorLocations = dao.monitorLocations
    }

    fun saveData() {
        dao.save()
    }
}