package io.github.yukileafx.yukimusic.command

import io.github.yukileafx.yukimusic.YukiMusic
import io.github.yukileafx.yukimusic.nbs.NBSInputStream
import io.github.yukileafx.yukimusic.nbs.bukkit.NBSSound
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.scheduler.BukkitRunnable
import java.io.ByteArrayInputStream
import java.net.URL
import kotlin.math.pow

class PlayNBSURLCommand(private val plugin: YukiMusic) : CommandExecutor {

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        if (args.isEmpty()) {
            return false
        }
        plugin.server.scheduler.runTaskAsynchronously(plugin) {
            try {
                val urlSpec = args.joinToString(" ")
                sender.sendMessage("Connecting to $urlSpec ...")

                val url = URL(urlSpec)
                val urlConn = url.openConnection()
                urlConn.setRequestProperty("User-Agent", "Mozilla/5.0")
                val inputStream = urlConn.getInputStream()
                val buf = inputStream.readBytes()
                val bufIn = ByteArrayInputStream(buf)
                val nbsIn = NBSInputStream(bufIn)

                val header = nbsIn.readHeader()
                sender.sendMessage(header.toString())

                val noteBlocks = nbsIn.readNoteBlocks(header)
                sender.sendMessage("Loaded ${noteBlocks.size} note blocks.")

                val multiplier = 20 / (header.songTempo / 100).toFloat()
                var tick = -1
                val realLength = header.songLength * multiplier
                val realNoteBlocks = noteBlocks.mapKeys { Pair((it.key.first * multiplier).toInt(), it.key.second) }
                sender.sendMessage("Song length: $realLength")

                object : BukkitRunnable() {
                    override fun run() {
                        tick += 1

                        if (tick >= realLength) {
                            cancel()
                            return
                        }

                        for (layer in 0 until header.layerCount) {
                            val pos = Pair(tick, layer)
                            val noteBlock = realNoteBlocks[pos] ?: continue

                            for (player in plugin.server.onlinePlayers) {
                                val sound = NBSSound.getBukkitSound(noteBlock.instrument)
                                val pitch = 2.toFloat().pow(-(12 - (noteBlock.key.toFloat() - 33)) / 12)

                                sender.sendMessage("$pos -> $noteBlock '$sound' #$pitch")
                                player.playSound(player.location, sound, 1f, pitch)
                            }
                        }
                    }
                }.runTaskTimer(plugin, 0, 1)
            } catch (t: Throwable) {
                sender.sendMessage("${ChatColor.RED}Failed to load NBS!")
                sender.sendMessage("${ChatColor.GRAY}$t")
            }
        }
        return true
    }
}
