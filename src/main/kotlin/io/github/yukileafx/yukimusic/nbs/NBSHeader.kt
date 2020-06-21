package io.github.yukileafx.yukimusic.nbs

data class NBSHeader(
    var newFormat: Boolean = false,

    var version: Byte = 0,
    var vanillaInstrumentCount: Byte = 0,
    var songLength: Short = 0,

    var layerCount: Short = 0,
    var songName: String = "",
    var songAuthor: String = "",
    var songOriginalAuthor: String = "",
    var songDescription: String = "",
    var songTempo: Short = 0,
    var autoSaving: Boolean = false,
    var autoSavingDuration: Byte = 0,
    var timeSignature: Byte = 0,
    var minutesSpent: Int = 0,
    var leftClicks: Int = 0,
    var rightClicks: Int = 0,
    var noteBlocksAdded: Int = 0,
    var noteBlocksRemoved: Int = 0,
    var importedFileName: String = "",

    var loop: Boolean = false,
    var maxLoopCount: Byte = 0,
    var loopStartTick: Short = 0
)
