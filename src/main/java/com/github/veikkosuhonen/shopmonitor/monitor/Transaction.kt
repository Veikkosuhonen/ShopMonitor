package com.github.veikkosuhonen.shopmonitor.monitor

import org.bukkit.Material
import java.io.Serializable

data class Transaction(val material: Material, val amount: Int): Serializable