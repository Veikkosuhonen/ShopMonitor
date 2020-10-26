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
    val installNames = HashMap<UUID, String>()

    override fun onEnable() {
        getData()
        val installer = Installer(installNames, monitors, monitorLocations)

        val eventListener = ContainerEventListener(monitorLocations, installer)
        server.pluginManager.registerEvents(eventListener, this)

        val loginEventListener = LoginEventListener(monitors)
        server.pluginManager.registerEvents(loginEventListener, this)

        val command = Command(installNames, monitors)
        getCommand("monitor")?.setExecutor(command)
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