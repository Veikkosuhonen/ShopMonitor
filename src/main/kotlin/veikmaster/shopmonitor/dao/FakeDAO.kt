package main.kotlin.veikmaster.shopmonitor.dao

import main.kotlin.veikmaster.shopmonitor.Monitor
import org.bukkit.Location
import java.util.*
import kotlin.collections.HashMap

class FakeDAO: DAO {
    override val monitors = HashMap<UUID, MutableList<Monitor>>()
    override val monitorLocations = HashMap<Location, Monitor>()

    override fun save() {

    }
}