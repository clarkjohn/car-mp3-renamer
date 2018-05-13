package com.clarkjohn.mp3.carrenamer

import com.clarkjohn.mp3.carrenamer.type.CarDisplayType
import java.io.File

val NUMERIC_REGEX = "\\d+".toRegex()
//val DISPLAY_DIVIDER_CHARACTER = "-"

//Change these
val mp3RootFolder = File("D:\\temp\\car-mp3-renamer")
val displayType = CarDisplayType.TOYOTA
val renameFiles = true

fun main(args: Array<String>) {

    require(mp3RootFolder.isDirectory)

    processSubfolders(mp3RootFolder, displayType)
    println("\nRenaming mp3 files and folders finished")
}

private fun processSubfolders(folder: File, carDisplayType: CarDisplayType) {

    println("\nin folder=$folder")

    val folderProperties = getFolderProperties(folder)
    println("folderProperties=$folderProperties")

    folderProperties.subDirectories.forEach { subdirectory ->
        processSubfolders(subdirectory, carDisplayType)
    }

    renameFiles(carDisplayType, folderProperties, folder)
    renameFolder(carDisplayType, folderProperties, folder)
}

private fun renameFolder(carDisplayType: CarDisplayType, folderProperties: FolderProperties, folder: File) {

    val newFolderName = carDisplayType.folderRenameStrategy.rename(folder, folderProperties)
    println("renaming folder=${folder.name} to=$newFolderName")

    if (renameFiles && newFolderName != folder.name) {
        folder.renameTo( File(newFolderName))
    }
}

private fun renameFiles(carDisplayType: CarDisplayType, folderProperties: FolderProperties, folder: File) {

    val ignoredFiles = mutableListOf<String>()
    folder.listFiles().forEach { file ->

        if (file.isDirectory) {
            //do nothing
        } else if (isMp3File(file)) {
            val newMp3FileName = carDisplayType.mp3RenameStrategy.rename(file, folderProperties)
            println("renaming file=${file.name.padEnd(folderProperties.largestFileLength)} to=$newMp3FileName")

            if (renameFiles && newMp3FileName != newMp3FileName) {
                file.renameTo( File(newMp3FileName))
            }

        } else {
            ignoredFiles.add(file.name)
        }
    }

    println("skipping non mp3 files=$ignoredFiles")
}

private fun isMp3File(file: File) = file.name.substring(file.name.lastIndexOf(".") + 1).equals("mp3", true)

private fun getFolderProperties(folder: File): FolderProperties {

    val subDirectories = mutableListOf<File>()

    var titleDisplayLength = 0
    var sampleAlbumName: String = ""
    var allFileInFolderShareSameAlbumName = true
    var maxFileNameLength = 0
    var lastMp3File: File? = null

    folder.listFiles().forEach { file ->
        if (isMp3File(file)) {

            lastMp3File = file

            val title = Mp3FileUtil.getTitle(file)
            if (titleDisplayLength < title.length) {
                titleDisplayLength = title.length
            }

            if (maxFileNameLength < file?.name?.length ?: -1) {
                maxFileNameLength = file.name.length
            }

            if (allFileInFolderShareSameAlbumName) {

                val album = Mp3FileUtil.getAlbum(file)

                if (sampleAlbumName == "") {
                    sampleAlbumName = album
                } else if (!sampleAlbumName.equals(album, true)) {
                    allFileInFolderShareSameAlbumName = false
                } else {
                    //do nothing; allFileInFolderShareSameAlbumName is still true
                }

            }
        } else if (file.isDirectory) {
            subDirectories.add(file)
        } else {
            //sdo nothing
        }
    }

    if (lastMp3File != null) {
        val lastNonNullMp3File = lastMp3File!!
        return FolderProperties(allFileInFolderShareSameAlbumName, maxFileNameLength, 2, subDirectories, Mp3FileUtil.getArtist(lastNonNullMp3File).trim(), Mp3FileUtil.getAlbum(lastNonNullMp3File).trim(), Mp3FileUtil.getYear(lastNonNullMp3File).trim())
    } else {
        return FolderProperties(allFileInFolderShareSameAlbumName, maxFileNameLength, 2, subDirectories, "", "", "")
    }
}

data class FolderProperties(val isOneAlbum: Boolean = false, val largestFileLength: Int = 0, val maxTrackLength: Int = 0, val subDirectories: List<File> = mutableListOf<File>(), val mp3Artist: String, val mp3Album: String, val mp3Year: String)
