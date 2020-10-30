package main.kotlin.veikmaster.shopmonitor.dao

import main.kotlin.veikmaster.shopmonitor.Monitor
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.util.io.BukkitObjectInputStream
import org.bukkit.util.io.BukkitObjectOutputStream
import java.io.*
import java.nio.file.Paths
import java.util.*
import java.util.logging.Level
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream

class FileDAO(filePath: String): DAO {
    private val path = Paths.get("").toAbsolutePath().toString() + filePath

    override fun load() {
        val file = File(path)
        val dao: DAO

        if (!file.exists()) {
            try {
                Bukkit.getServer().logger.log(Level.INFO, "[ShopMonitor] could not find datafile, creating empty")
                file.parentFile.mkdir()
                file.createNewFile()
            } catch(e: IOException) {
                e.printStackTrace()
            }
            dao = FakeDAO()
            dao.load()
        } else {
            dao = try {
                val input = BukkitObjectInputStream(GZIPInputStream(FileInputStream(path)))
                val result = input.readObject() as DAO
                input.close()
                Bukkit.getServer().logger.log(Level.INFO, "[ShopMonitor] datafile loaded")
                result
            } catch (e: IOException) {
                Bukkit.getServer().logger.log(Level.WARNING, "[ShopMonitor] failed to load datafile")
                val result = FakeDAO()
                result.load()
                result
            } catch (e: InvalidClassException) {
                Bukkit.getServer().logger.log(Level.WARNING, "[ShopMonitor] failed to load datafile, save file may be incompatible")
                val result = FakeDAO()
                result.load()
                result
            }
        }

        monitors = dao.monitors
        monitorLocations = dao.monitorLocations
    }

    override lateinit var monitors: HashMap<UUID, MutableList<Monitor>>
    override lateinit var monitorLocations: HashMap<Location, Monitor>

    override fun save() {
        try {
            val out = BukkitObjectOutputStream(GZIPOutputStream(FileOutputStream(path)))
            out.writeObject(this)
            out.close();
            Bukkit.getServer().logger.log(Level.INFO, "[ShopMonitor] data saved")
        } catch (e: IOException) {
            Bukkit.getServer().logger.log(Level.WARNING, "[ShopMonitor] failed to save datafile")
            e.printStackTrace()
        }
    }
}