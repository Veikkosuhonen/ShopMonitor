package main.kotlin.veikmaster.shopmonitor.dao

import main.kotlin.veikmaster.shopmonitor.Monitor
import org.bukkit.Location
import java.io.Serializable
import java.util.*

interface DAO: Serializable {
    public abstract var monitors: HashMap<UUID, MutableList<Monitor>>
    public abstract var monitorLocations: HashMap<Location, Monitor>
    public abstract fun load()
    public abstract fun save()
}