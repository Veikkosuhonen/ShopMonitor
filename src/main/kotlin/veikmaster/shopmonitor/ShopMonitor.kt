package main.kotlin.veikmaster.shopmonitor

import main.kotlin.veikmaster.shopmonitor.dao.FakeDAO
import main.kotlin.veikmaster.shopmonitor.dao.FileDAO
import org.bukkit.Location
import org.bukkit.plugin.java.JavaPlugin
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet

class ShopMonitor: JavaPlugin() {

    val dao = FileDAO("\\plugins\\ShopMonitor\\data")
    lateinit var monitors: HashMap<UUID, MutableList<Monitor>>
    lateinit var monitorLocations: HashMap<Location, Monitor>
    val installNames = HashMap<UUID, String>()
    val trueSight = HashSet<UUID>()

    override fun onEnable() {
        loadData()
        val installer = Installer(installNames, monitors, monitorLocations)

        val eventListener = ContainerEventListener(monitorLocations, installer, trueSight)
        server.pluginManager.registerEvents(eventListener, this)

        val loginEventListener = LoginEventListener(monitors)
        server.pluginManager.registerEvents(loginEventListener, this)

        val command = Command(installNames, monitors)
        getCommand("monitor")?.setExecutor(command)

        val opCommand = OPCommand(trueSight)
        getCommand("monitorop")?.setExecutor(opCommand)
    }

    override fun onDisable() {
        saveData()
    }

    fun loadData() {
        dao.load()
        monitors = dao.monitors
        monitorLocations = dao.monitorLocations
    }

    fun saveData() {
        dao.save()
    }
}