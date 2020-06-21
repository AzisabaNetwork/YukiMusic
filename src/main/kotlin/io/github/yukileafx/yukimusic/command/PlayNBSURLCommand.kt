package io.github.yukileafx.yukimusic.command

import io.github.yukileafx.yukimusic.YukiMusic
import io.github.yukileafx.yukimusic.nbs.NBSInputStream
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import java.net.URL

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
                val nbsIn = NBSInputStream(inputStream)

                val header = nbsIn.readHeader()
                sender.sendMessage(header.toString())
            } catch (t: Throwable) {
                sender.sendMessage("${ChatColor.RED}Failed to load NBS!")
                sender.sendMessage("${ChatColor.GRAY}$t")
            }
        }
        return true
    }
}
