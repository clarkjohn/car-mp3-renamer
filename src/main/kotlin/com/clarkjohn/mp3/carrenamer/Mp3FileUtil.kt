package com.clarkjohn.mp3.carrenamer

import com.mpatric.mp3agic.Mp3File
import java.io.File

class Mp3FileUtil {

    companion object {

        fun getTitle(file: File): String {
            val mp3File = Mp3File(file.absoluteFile)

            if (mp3File.hasId3v2Tag()) {
                return mp3File.id3v2Tag?.title ?: ""
            } else if (mp3File.hasId3v1Tag()) {
                return mp3File.id3v1Tag?.title ?: ""
            } else {
                return ""
            }
        }

        fun getAlbum(file: File): String {
            val mp3File = Mp3File(file.absoluteFile)

            if (mp3File.hasId3v2Tag()) {
                return mp3File.id3v2Tag?.album ?: ""
            } else if (mp3File.hasId3v1Tag()) {
                return mp3File.id3v1Tag?.album ?: ""
            } else {
                return ""
            }
        }

        fun getYear(file: File): String {
            val mp3File = Mp3File(file.absoluteFile)

            if (mp3File.hasId3v2Tag()) {
                return mp3File.id3v2Tag?.year ?: ""
            } else if (mp3File.hasId3v1Tag()) {
                return mp3File.id3v1Tag?.year ?: ""
            } else {
                return ""
            }
        }

        fun getTrack(file: File): String {
            val mp3File = Mp3File(file.absoluteFile)

            if (mp3File.hasId3v2Tag()) {
                return mp3File.id3v2Tag?.track ?: ""
            } else if (mp3File.hasId3v1Tag()) {
                return mp3File.id3v1Tag?.track ?: ""
            } else {
                return ""
            }
        }

        fun getArtist(file: File): String {
            val mp3File = Mp3File(file.absoluteFile)

            if (mp3File.hasId3v2Tag()) {
                return mp3File.id3v2Tag?.artist ?: ""
            } else if (mp3File.hasId3v1Tag()) {
                return mp3File.id3v1Tag?.artist ?: ""
            } else {
                return ""
            }
        }
    }
}