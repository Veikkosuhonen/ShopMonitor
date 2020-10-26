package main.kotlin.veikmaster.shopmonitor

import org.bukkit.Location
import java.io.Serializable
import java.util.*

class Monitor(val transactions: MutableList<Transaction>): Serializable {

    fun addTransaction(transaction: Transaction) {
        transactions.add(transaction)
    }

    fun clear() {
        transactions.clear()
    }
}