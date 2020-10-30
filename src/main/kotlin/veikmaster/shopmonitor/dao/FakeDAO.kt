package main.kotlin.veikmaster.shopmonitor.dao

import main.kotlin.veikmaster.shopmonitor.Monitor
import org.bukkit.Location
import java.util.*
import kotlin.collections.HashMap

class FakeDAO: DAO {
    override lateinit var monitors: HashMap<UUID, MutableList<Monitor>>
    override lateinit var monitorLocations: HashMap<Location, Monitor>

    override fun save() {

    }

    override fun load() {
        monitorLocations = HashMap<Location, Monitor>()
        monitors = HashMap<UUID, MutableList<Monitor>>()
    }
}