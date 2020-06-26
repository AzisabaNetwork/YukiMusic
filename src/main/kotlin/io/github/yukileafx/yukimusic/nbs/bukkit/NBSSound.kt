package io.github.yukileafx.yukimusic.nbs.bukkit

import org.bukkit.Sound

enum class NBSSound(val instrument: Byte, vararg val sounds: String) {

    HARP(0, "BLOCK_NOTE_HARP", "BLOCK_NOTE_BLOCK_HARP"),
    BASS(1, "BLOCK_NOTE_BASS", "BLOCK_NOTE_BLOCK_BASS"),
    BASEDRUM(2, "BLOCK_NOTE_BASEDRUM", "BLOCK_NOTE_BLOCK_BASEDRUM"),
    SNARE(3, "BLOCK_NOTE_SNARE", "BLOCK_NOTE_BLOCK_SNARE"),
    HAT(4, "BLOCK_NOTE_HAT", "BLOCK_NOTE_BLOCK_HAT"),
    GUITAR(5, "BLOCK_NOTE_GUITAR", "BLOCK_NOTE_BLOCK_GUITAR"),
    FLUTE(6, "BLOCK_NOTE_FLUTE", "BLOCK_NOTE_BLOCK_FLUTE"),
    BELL(7, "BLOCK_NOTE_BELL", "BLOCK_NOTE_BLOCK_BELL"),
    CHIME(8, "BLOCK_NOTE_CHIME", "BLOCK_NOTE_BLOCK_CHIME"),
    XYLOPHONE(9, "BLOCK_NOTE_XYLOPHONE", "BLOCK_NOTE_BLOCK_XYLOPHONE"),
    IRON_XYLOPHONE(10, "BLOCK_NOTE_BLOCK_IRON_XYLOPHONE"),
    COW_BELL(11, "BLOCK_NOTE_BLOCK_COW_BELL"),
    DIDGERIDOO(12, "BLOCK_NOTE_BLOCK_DIDGERIDOO"),
    BIT(13, "BLOCK_NOTE_BLOCK_BIT"),
    BANJO(14, "BLOCK_NOTE_BLOCK_BANJO"),
    PLING(15, "BLOCK_NOTE_BLOCK_PLING");

    companion object {

        fun getBukkitSound(instrument: Byte) =
            (values().find { it.instrument == instrument } ?: HARP)
                .sounds
                .map { kotlin.runCatching { Sound.valueOf(it) }.getOrNull() }
                .firstOrNull { it != null }!!
    }
}
