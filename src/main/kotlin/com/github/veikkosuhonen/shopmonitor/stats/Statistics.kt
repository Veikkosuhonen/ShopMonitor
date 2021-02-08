package com.github.veikkosuhonen.shopmonitor.stats

import com.github.veikkosuhonen.shopmonitor.monitor.Monitor
import org.bukkit.Material
import java.util.*
import kotlin.collections.HashMap

class Statistics(val monitors: HashMap<UUID, MutableList<Monitor>>) {

    fun getUserCount(): Int {
        return monitors.values.count { it.isNotEmpty() }
    }

    fun getMonitorCount(): Int {
        return monitors.values.sumBy { it.size }
    }

    fun getAvgMonitorsPerUser(): Double {
        return getMonitorCount().toDouble() / getUserCount()
    }

    fun getVolumeOfMaterial(material: Material): Int {
        material ?: return 0
        return monitors.values.sumBy { it.sumBy { it.transactions.count { it.material == material } } }
    }
}