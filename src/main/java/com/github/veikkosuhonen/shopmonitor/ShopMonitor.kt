package com.github.veikkosuhonen.shopmonitor

import com.github.veikkosuhonen.shopmonitor.command.Command
import com.github.veikkosuhonen.shopmonitor.command.CommandTabCompleter
import com.github.veikkosuhonen.shopmonitor.command.OPCommand
import com.github.veikkosuhonen.shopmonitor.command.OPCommandTabCompleter
import com.github.veikkosuhonen.shopmonitor.dao.FileDAO
import com.github.veikkosuhonen.shopmonitor.event.ContainerEventListener
import com.github.veikkosuhonen.shopmonitor.event.LoginEventListener
import com.github.veikkosuhonen.shopmonitor.monitor.Installer
import com.github.veikkosuhonen.shopmonitor.monitor.Monitor
import org.bukkit.Location
import org.bukkit.plugin.java.JavaPlugin
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet

class ShopMonitor: JavaPlugin() {

    val dao = FileDAO("./plugins/ShopMonitor/data")
    lateinit var monitors: HashMap<UUID, MutableList<Monitor>>
    lateinit var monitorLocations: HashMap<Location, Monitor>
    val installNames = HashMap<UUID, String>()
    val trueSight = HashSet<UUID>()

    override fun onEnable() {
        loadData()
        val installer = Installer(installNames, monitors, monitorLocations)

        // ------- Event listeners -------------------------
        val eventListener = ContainerEventListener(monitorLocations, installer, trueSight)
        server.pluginManager.registerEvents(eventListener, this)
        val loginEventListener = LoginEventListener(monitors)
        server.pluginManager.registerEvents(loginEventListener, this)

        // ------- Monitor command -------------------------
        val command = Command(monitors, installer)
        getCommand("monitor")?.setExecutor(command)
        val commandTabCompleter = CommandTabCompleter(monitors)
        getCommand("monitor")?.tabCompleter = commandTabCompleter

        // ------ Operator command -------------------------
        val opCommand = OPCommand(trueSight)
        getCommand("monitorop")?.setExecutor(opCommand)
        val opCommandTabCompleter = OPCommandTabCompleter()
        getCommand("monitorop")?.tabCompleter = opCommandTabCompleter
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