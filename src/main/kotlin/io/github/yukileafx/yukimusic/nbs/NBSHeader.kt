package io.github.yukileafx.yukimusic.nbs

class NBSHeader {

    var newFormat = false
    var version = 0.toByte()
    var vanillaInstrumentCount = 0.toByte()
    var songLength = 0.toShort()

    var layerCount = 0.toShort()
    var songName = ""
    var songAuthor = ""
    var songOriginalAuthor = ""
    var songDescription = ""
}
