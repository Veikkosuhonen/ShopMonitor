package main.kotlin.veikmaster.shopmonitor

import org.bukkit.Material
import java.io.Serializable

data class Transaction(val material: Material, val amount: Int): Serializable