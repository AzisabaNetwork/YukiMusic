package io.github.yukileafx.yukimusic.command

import io.github.yukileafx.yukimusic.YukiMusic
import io.github.yukileafx.yukimusic.nbs.NBS
import io.github.yukileafx.yukimusic.nbs.bukkit.NBSDebugPlayer
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import java.io.ByteArrayInputStream
import java.net.URL

class PlayNBSURLCommand(private val plugin: YukiMusic) : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) {
            return false
        }

        plugin.server.scheduler.runTaskAsynchronously(plugin) {
            try {
                val urlSpec = args.joinToString(" ")
                sender.sendMessage("Connecting to $urlSpec ...")

                val urlConn = URL(urlSpec).openConnection()
                urlConn.setRequestProperty("User-Agent", "Mozilla/5.0")
                val `in` = ByteArrayInputStream(urlConn.getInputStream().readBytes())

                val nbs = NBS.decode(`in`)
                sender.sendMessage("Loaded ${nbs.noteBlocks.size} note blocks.")

                val nbsPlayer = NBSDebugPlayer(plugin, nbs, plugin.server.onlinePlayers)
                sender.sendMessage("Song length: ${nbsPlayer.realLength}")

                nbsPlayer.playing = true
            } catch (t: Throwable) {
                sender.sendMessage("${ChatColor.RED}Failed to load NBS!\n${ChatColor.GRAY}$t")
            }
        }
        return true
    }
}
