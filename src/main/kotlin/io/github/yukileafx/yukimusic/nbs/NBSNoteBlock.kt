package io.github.yukileafx.yukimusic.nbs

data class NBSNoteBlock(
    var instrument: Byte = -1,
    var key: Byte = -1,
    var velocity: Byte = 100,
    var panning: Byte = 100,
    var pitch: Short = 0
)
