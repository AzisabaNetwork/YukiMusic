package io.github.yukileafx.yukimusic.nbs

import java.io.InputStream

data class NBS(
    var header: NBSHeader,
    var noteBlocks: NBSNoteBlocks
) {

    companion object {

        fun decode(`in`: InputStream): NBS {
            val nbsIn = NBSInputStream(`in`)

            val header = nbsIn.readHeader()
            val noteBlocks = nbsIn.readNoteBlocks(header)

            return NBS(header, noteBlocks)
        }
    }
}
