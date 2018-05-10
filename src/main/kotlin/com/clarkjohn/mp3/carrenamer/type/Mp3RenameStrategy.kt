package com.clarkjohn.mp3.carrenamer.type

import com.clarkjohn.mp3.carrenamer.FolderProperties
import com.clarkjohn.mp3.carrenamer.Mp3FileUtil
import java.io.File

val ALL_NON_NUMERIC_REGEX = """[^0-9.]""".toRegex()

enum class Mp3RenameStrategy {

    OPTIMAL {
        override fun rename(mp3File: File, folderProperties: FolderProperties): String {

            if (folderProperties.isOneAlbum) {
                return SHOW_TRACK_NUMBER_AND_NAME.rename(mp3File, folderProperties)
            } else {
                return SHOW_NAME.rename(mp3File, folderProperties)
            }
        }
    },

    SHOW_TRACK_NUMBER_AND_NAME {
        override fun rename(mp3File: File, folderProperties: FolderProperties): String {

            val mp3Title = Mp3FileUtil.getTitle(mp3File)
            if (mp3Title.isNullOrEmpty()) {
                //just return original file name
                return mp3File.name
            } else {
                return cleanTrack(Mp3FileUtil.getTrack(mp3File).trim(), 2).padStart(2, '0') + " " + mp3Title + ".mp3"
            }
        }

        private fun cleanTrack(track: String, maxTrackLength: Int): String {

            val trimmedTrack = if (track.length > maxTrackLength) track.substring(0, maxTrackLength) else track
            return trimmedTrack.replace(ALL_NON_NUMERIC_REGEX, "")
        }

    },

    SHOW_NAME {
        override fun rename(mp3File: File, folderProperties: FolderProperties): String {

            return mp3File.name
        }
    };

    abstract fun rename(mp3File: File, folderProperties: FolderProperties): String
}