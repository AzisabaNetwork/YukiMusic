package io.github.yukileafx.yukimusic.nbs.bukkit

import org.bukkit.Sound

object NBSSound {

    fun getBukkitSound(instrument: Byte): Sound {
        return when (instrument) {
            0.toByte() -> Sound.BLOCK_NOTE_HARP
            1.toByte() -> Sound.BLOCK_NOTE_BASS
            2.toByte() -> Sound.BLOCK_NOTE_BASEDRUM
            3.toByte() -> Sound.BLOCK_NOTE_SNARE
            4.toByte() -> Sound.BLOCK_NOTE_HAT
            5.toByte() -> Sound.BLOCK_NOTE_GUITAR
            6.toByte() -> Sound.BLOCK_NOTE_FLUTE
            7.toByte() -> Sound.BLOCK_NOTE_BELL
            8.toByte() -> Sound.BLOCK_NOTE_CHIME
            9.toByte() -> Sound.BLOCK_NOTE_XYLOPHONE
            else -> Sound.BLOCK_NOTE_HARP
        }
    }
}
