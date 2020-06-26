package io.github.yukileafx.yukimusic

import io.github.yukileafx.yukimusic.command.PlayNBSURLCommand
import org.bukkit.plugin.java.JavaPlugin

class YukiMusic : JavaPlugin() {

    override fun onEnable() {
        getCommand("play-nbs-url")?.setExecutor(PlayNBSURLCommand(this))
    }
}
