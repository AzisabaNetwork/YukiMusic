package io.github.yukileafx.yukimusic.nbs

import java.io.DataInputStream
import java.io.InputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.math.min

class NBSInputStream(`in`: InputStream) : DataInputStream(`in`) {

    private fun readLittleEndianShort() =
        ByteArray(2)
            .apply { read(this) }
            .let { ByteBuffer.wrap(it).order(ByteOrder.LITTLE_ENDIAN).short }

    private fun readLittleEndianInt() =
        ByteArray(4)
            .apply { read(this) }
            .let { ByteBuffer.wrap(it).order(ByteOrder.LITTLE_ENDIAN).int }

    private fun readString() =
        let {
            val len = min(available(), readLittleEndianInt())
            ByteArray(len)
        }
            .apply { read(this) }
            .toString(Charsets.UTF_8)

    fun readHeader() =
        NBSHeader()
            .apply {
                val first2Byte = readLittleEndianShort()

                newFormat = first2Byte.toInt() == 0

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

    fun readNoteBlocks(header: NBSHeader) =
        NBSNoteBlocks()
            .apply {
                var tick = -1
                while (true) {
                    val moreTicks = readLittleEndianShort().toInt()

                    takeIf { moreTicks != 0 }
                        ?.run { tick += moreTicks }
                        ?: break

                    var layer = -1
                    while (true) {
                        val moreLayers = readLittleEndianShort().toInt()

                        takeIf { moreLayers != 0 }
                            ?.run { layer += moreLayers }
                            ?: break

                        val pos = Pair(tick, layer)
                        val noteBlock = NBSNoteBlock()
                            .apply {
                                instrument = readByte()
                                key = readByte()

                                if (header.version >= 4) {
                                    velocity = readByte()
                                    panning = readByte()
                                    pitch = readLittleEndianShort()
                                }
                            }

                        put(pos, noteBlock)
                    }
                }
            }
}
