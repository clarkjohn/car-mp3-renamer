package com.clarkjohn.mp3.carrenamer.type

import com.clarkjohn.mp3.carrenamer.FolderProperties
import com.clarkjohn.mp3.carrenamer.NUMERIC_REGEX
import java.io.File
import java.time.Year

const val PREFIX_CHARACTER_TO_SORT_2_DIGIT_YEAR_BEFORE_YEAR_2000 = "."

//TODO move this into CarDisplayType
const val DISPLAY_ARTIST_MAX_SIZE = 14

val WORD_REGEX = "\\W+".toRegex()
val FIRST_LETTER_OF_WORD_REGEX = "\\B.|\\P{L}".toRegex()

enum class FolderRenameStrategy {

    OPTIMAL {
        override fun rename(folder: File, folderProperties: FolderProperties): String {
            if (folderProperties.isOneAlbum) {
                return SHOW_ARTIST_YEAR_ALBUM.rename(folder, folderProperties)
            } else {
                //mixed artists in folder, return current folder name
                return folder.name
            }
        }
    },

    SHOW_ARTIST_YEAR_ALBUM {
        override fun rename(folder: File, folderProperties: FolderProperties): String {

            if (folderProperties.isOneAlbum && !folderProperties.mp3Artist.isEmpty() && !folderProperties.mp3Album.isEmpty()) {

                val displayArtist = getDisplayArtist(folderProperties.mp3Artist)

                val displayYear = getDisplayYear(folderProperties.mp3Year)
                if (!displayYear.isEmpty()) {
                    return "$displayArtist ($displayYear) ${folderProperties.mp3Album}"
                } else {
                    return "$displayArtist - ${folderProperties.mp3Album}"
                }

            } else {
                return folder.name
            }
        }

        private fun getDisplayArtist(mp3Artist: String): String {
            if (getNumberOfWords(mp3Artist) > 2) {
                return toAcronym(mp3Artist)
            } else if (mp3Artist.length > DISPLAY_ARTIST_MAX_SIZE) {
                return mp3Artist.substring(0, DISPLAY_ARTIST_MAX_SIZE)
            } else {
                return mp3Artist
            }
        }

        private fun toAcronym(mp3Artist: String) = mp3Artist.replace(FIRST_LETTER_OF_WORD_REGEX, "").toUpperCase()

        private fun getNumberOfWords(mp3Artist: String) = mp3Artist.split(WORD_REGEX).size

        private fun getDisplayYear(mp3Year: String): String {

            if (mp3Year.length == 4 && mp3Year.matches(NUMERIC_REGEX) && (isMp3YearWithinLast100Years(mp3Year))) {
                return (if (isYearBeforeStartOfCentury(mp3Year)) PREFIX_CHARACTER_TO_SORT_2_DIGIT_YEAR_BEFORE_YEAR_2000 else "") + mp3Year.substring(2, 4)
            } else {
                //unknown/garbage year format
                return ""
            }
        }

        private fun isYearBeforeStartOfCentury(year: String) = year.toInt() < 2000

        private fun isMp3YearWithinLast100Years(year: String) = year.toInt() - 100 < Year.now().value
    };

    abstract fun rename(folder: File, folderProperties: FolderProperties): String
}