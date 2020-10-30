package main.kotlin.veikmaster.shopmonitor

import org.bukkit.Location
import java.io.Serializable

class Monitor(val transactions: MutableList<Transaction>,
              val contents: MutableList<Transaction>,
              val location: Location,
              var name: String,
             ): Serializable {

    constructor(location: Location, name: String): this(mutableListOf(), mutableListOf(), location, name, )

    var unread = false

    fun clear() {
        unread = false
        transactions.clear()
    }
}