package io.github.yukileafx.yukimusic.nbs

import java.io.DataInputStream
import java.io.InputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder

class NBSInputStream(inputStream: InputStream) : DataInputStream(inputStream) {

    fun readLittleEndianShort() =
        ByteArray(2)
            .apply { read(this) }
            .let { ByteBuffer.wrap(it).order(ByteOrder.LITTLE_ENDIAN).short }

    fun readLittleEndianInt() =
        ByteArray(4)
            .apply { read(this) }
            .let { ByteBuffer.wrap(it).order(ByteOrder.LITTLE_ENDIAN).int }

    fun readString() =
        let {
            val len = readLittleEndianInt()
            ByteArray(len)
        }
            .apply { read(this) }
            .toString(Charsets.UTF_8)

    fun readHeader() =
        NBSHeader()
            .apply {
                val first2Byte = readLittleEndianShort()

                newFormat = first2Byte == 0.toShort()

                if (newFormat) {
                    version = readByte()
                    vanillaInstrumentCount = readByte()
                    if (version >= 3) {
                        songLength = readLittleEndianShort()
                    }
                } else {
                    songLength = first2Byte
                }

                layerCount = readLittleEndianShort()
                songName = readString()
                songAuthor = readString()
                songOriginalAuthor = readString()
                songDescription = readString()
                songTempo = readLittleEndianShort()
                autoSaving = readBoolean()
                autoSavingDuration = readByte()
                timeSignature = readByte()
                minutesSpent = readLittleEndianInt()
                leftClicks = readLittleEndianInt()
                rightClicks = readLittleEndianInt()
                noteBlocksAdded = readLittleEndianInt()
                noteBlocksRemoved = readLittleEndianInt()
                importedFileName = readString()

                if (version >= 4) {
                    loop = readBoolean()
                    maxLoopCount = readByte()
                    loopStartTick = readLittleEndianShort()
                }
            }
}
