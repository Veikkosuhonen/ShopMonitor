package main.kotlin.veikmaster.shopmonitor.dao

import main.kotlin.veikmaster.shopmonitor.Monitor
import org.bukkit.Location
import java.io.Serializable
import java.util.*
import kotlin.collections.HashMap

interface DAO: Serializable {
    public abstract val monitors: HashMap<UUID, MutableList<Monitor>>
    public abstract val monitorLocations: HashMap<Location, Monitor>
    public abstract fun save()
}