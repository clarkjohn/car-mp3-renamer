package com.clarkjohn.mp3.carrenamer.type

enum class CarDisplayType(val mp3RenameStrategy: Mp3RenameStrategy, val folderRenameStrategy: FolderRenameStrategy) {

    FORD(Mp3RenameStrategy.SHOW_NAME, FolderRenameStrategy.OPTIMAL),
    HYUNDAI(Mp3RenameStrategy.OPTIMAL, FolderRenameStrategy.OPTIMAL),
    TOYOTA(Mp3RenameStrategy.OPTIMAL, FolderRenameStrategy.OPTIMAL),
}



