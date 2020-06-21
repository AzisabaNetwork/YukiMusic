package io.github.yukileafx.yukimusic.nbs

import java.io.DataInputStream
import java.io.InputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder

class NBSInputStream(`in`: InputStream) : DataInputStream(`in`) {

    private fun readLittleEndianShort(): Short =
        ByteArray(2)
            .apply { read(this) }
            .let { ByteBuffer.wrap(it).order(ByteOrder.LITTLE_ENDIAN).short }

    private fun readLittleEndianInt(): Int =
        ByteArray(4)
            .apply { read(this) }
            .let { ByteBuffer.wrap(it).order(ByteOrder.LITTLE_ENDIAN).int }

    private fun readString(): String {
        val len = readLittleEndianInt()
        return ByteArray(len)
            .apply { read(this) }
            .toString(Charsets.UTF_8)
    }

    fun readHeader(): NBSHeader {
        val header = NBSHeader()

        val first2Byte = readLittleEndianShort()

        if (first2Byte == 0.toShort()) {
            header.newFormat = true
            header.version = readByte()
            header.vanillaInstrumentCount = readByte()
            header.songLength = readLittleEndianShort()
        } else {
            header.songLength = first2Byte
        }

        header.layerCount = readLittleEndianShort()
        header.songName = readString()
        header.songAuthor = readString()
        header.songOriginalAuthor = readString()
        header.songDescription = readString()

        return header
    }
}
