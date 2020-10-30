package com.github.veikkosuhonen.shopmonitor

import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Bukkit
import org.bukkit.entity.Player

object Messager { //that's a singleton

    fun sendLoginMessage(player: Player) {
        player.spigot().sendMessage(ChatMessageType.SYSTEM, *ComponentBuilder() //Spread op. convert array to varargs
                .append("$prefix §b§lYou have unread events! §r")
                .append(TextComponent("§a[view]§r"))
                .event(HoverEvent(HoverEvent.Action.SHOW_TEXT, arrayOf(TextComponent("View events"))))
                .event(ClickEvent(ClickEvent.Action.RUN_COMMAND, "/monitor view"))
                .create()
        )
    }

    fun sendView(player: Player, monitors: MutableList<Monitor>?, detailed: Boolean = false) {
        var builder = ComponentBuilder("§l§nShopMonitor view§r")

        if (monitors.isNullOrEmpty()) {
            player.spigot().sendMessage(*builder
                    .append("§e§o\nYou do not have any monitors. \nTo add new monitors, use ")
                    .append(TextComponent(" §a[This command]§r"))
                    .event(HoverEvent(HoverEvent.Action.SHOW_TEXT, arrayOf(TextComponent("Click"))))
                    .event(ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/monitor install <name>"))
                    .create())
            return
        }

        monitors.forEach { it ->
            monitorView(builder, it, player)
            it.unread = false
        }

        builder.append(TextComponent("\n§3[Add new]"))
        builder.event(HoverEvent(HoverEvent.Action.SHOW_TEXT, arrayOf(TextComponent("Click"))))
        builder.event(ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/monitor install <name>"))

        player.spigot().sendMessage(*builder.create())

    }

    fun sendDeleteConfirmMessage(player: Player, name: String) {
        player.spigot().sendMessage(*ComponentBuilder()
                .append("$prefix §6§l$name§3 will be deleted§r")
                .append(TextComponent(" §c§l[confirm]§r"))
                .event(HoverEvent(HoverEvent.Action.SHOW_TEXT, arrayOf(TextComponent("Click to confirm"))))
                .event(ClickEvent(ClickEvent.Action.RUN_COMMAND, "/monitor delete $name confirm"))
                .create()
        )
    }

    fun sendMessage(player: Player, message: String) {
        player.sendMessage("$prefix $message")
    }

    fun sendTrueSight(player: Player, monitor: Monitor) {
        val builder = ComponentBuilder("§lView of §r" + (Bukkit.getPlayer(monitor.ownerId)?.displayName ?: "[NOT FOUND]") + "'s §lmonitor")
        monitorView(builder, monitor, player)
        player.spigot().sendMessage(*builder.create())
    }

    private fun monitorView(builder: ComponentBuilder, it: Monitor, player: Player) {
        builder.append(TextComponent("\n§6§l" + it.name + "§r"))
        builder.event(HoverEvent(HoverEvent.Action.SHOW_TEXT, arrayOf(TextComponent(
                "" + it.location.x + " " + it.location.y + " " + it.location.z
        ))))

        builder.append(TextComponent(" §a[c]§r"))
        builder.event(HoverEvent(HoverEvent.Action.SHOW_TEXT, arrayOf(TextComponent("Clear events"))))
        builder.event(ClickEvent(ClickEvent.Action.RUN_COMMAND, "/monitor clear " + it.name))

        builder.append(TextComponent(" §e[r]§r"))
        builder.event(HoverEvent(HoverEvent.Action.SHOW_TEXT, arrayOf(TextComponent("Rename this monitor"))))
        builder.event(ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/monitor rename " + it.name + " <newname>"))

        builder.append(TextComponent(" §c[x]§r"))
        builder.event(HoverEvent(HoverEvent.Action.SHOW_TEXT, arrayOf(TextComponent("Delete this monitor"))))
        builder.event(ClickEvent(ClickEvent.Action.RUN_COMMAND, "/monitor delete " + it.name))

        player.spigot().sendMessage(*builder.create())
        builder.parts.clear()

        if (it.transactions.isEmpty()) {
            builder.append(" §7§oNo events§r\n")
        } else {
            it.transactions.forEach { t ->
                val name = t.material.toString().toLowerCase().replace('_', ' ').capitalize()
                if (t.amount > 0) {
                    builder.append(TextComponent(" §a+" + t.amount + " §r" + name + "\n"))
                } else {
                    builder.append(TextComponent(" §c" + t.amount + " §r" + name + "\n"))
                }
            }
        }
    }

}

const val prefix = "[ShopMonitor]"