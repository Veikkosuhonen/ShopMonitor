package main.kotlin.veikmaster.shopmonitor

import java.io.Serializable

class Monitor(val transactions: MutableList<Transaction>,
              val contents: MutableList<Transaction>,
              var name: String,
             ): Serializable {
    constructor(name: String): this(mutableListOf(), mutableListOf(), name)

    var unread = false

    fun clear() {
        unread = false
        transactions.clear()
    }
}