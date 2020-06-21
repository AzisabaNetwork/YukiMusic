package io.github.yukileafx.yukimusic.nbs.bukkit

import io.github.yukileafx.yukimusic.nbs.NBS
import io.github.yukileafx.yukimusic.nbs.NBSNoteBlock
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable
import kotlin.math.pow

open class NBSPlayer(plugin: Plugin, nbs: NBS, players: Collection<Player>) {

    private val multiplier = 20 / (nbs.header.songTempo / 100).toFloat()
    private val realNoteBlocks = nbs.noteBlocks.mapKeys { Pair((it.key.first * multiplier).toInt(), it.key.second) }
    val realLength = nbs.header.songLength * multiplier

    var currentTick = -1
    var playing = false

    init {
        object : BukkitRunnable() {
            override fun run() {
                if (!playing) {
                    return
                }

                currentTick++

                if (currentTick >= realLength) {
                    onFinish()
                    cancel()
                    return
                }

                for (layer in 0 until nbs.header.layerCount) {
                    val pos = Pair(currentTick, layer)
                    val noteBlock = realNoteBlocks[pos] ?: continue

                    for (player in players) {
                        val sound = NBSSound.getBukkitSound(noteBlock.instrument)
                        val pitch = 2.toFloat().pow(-(12 - (noteBlock.key.toFloat() - 33)) / 12)

                        onSound(player, pos, noteBlock, sound, pitch)
                        player.playSound(player.location, sound, 1f, pitch)
                    }
                }
            }
        }.runTaskTimer(plugin, 0, 1)
    }

    open fun onSound(player: Player, pos: Pair<Int, Int>, noteBlock: NBSNoteBlock, sound: Sound, pitch: Any) {}

    open fun onFinish() {}
}
