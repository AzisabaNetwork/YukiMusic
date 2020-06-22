package io.github.yukileafx.yukimusic.nbs.bukkit

import io.github.yukileafx.yukimusic.nbs.NBS
import io.github.yukileafx.yukimusic.nbs.NBSNoteBlock
import org.bukkit.Sound
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

class NBSDebugPlayer(
    private val author: CommandSender,
    plugin: Plugin,
    nbs: NBS,
    players: Collection<Player>
) :
    NBSPlayer(plugin, nbs, players) {

    override fun onSound(player: Player, pos: Pair<Int, Int>, noteBlock: NBSNoteBlock, sound: Sound, pitch: Any) {
        author.sendMessage("${player.name}: $pos -> $noteBlock '$sound' #$pitch")
    }

    override fun onFinish() {
        author.sendMessage("Complete!")
    }
}
