package main.kotlin.veikmaster.shopmonitor

import org.bukkit.Location
import java.io.Serializable
import java.util.*

class Monitor(val transactions: MutableList<Transaction>,
              val contents: MutableList<Transaction>,
              val location: Location,
              var name: String,
              val ownerId: UUID,
             ): Serializable {

    constructor(location: Location, name: String, ownerId: UUID): this(mutableListOf(), mutableListOf(), location, name, ownerId)

    var unread = false

    fun clear() {
        unread = false
        transactions.clear()
    }
}