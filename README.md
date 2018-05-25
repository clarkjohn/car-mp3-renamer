car-mp3-renamer
======================
Rename mp3s and folders  to be readable on a car mp3 player

## Usage Instructions
1. Update the following lines in [CarMp3Renamer.kt](car-mp3-renamer/src/main/kotlin/com/clarkjohn/mp3/carrenamer/CarMp3Renamer.kt):

``` Kotlin
val mp3RootFolder = File("e:\\root folder of usb drive containing mp3s")
val displayType = CarDisplayType.<Whatever car type, see CarDisplayType.kt>
val renameFiles = true

```

2. Execute [CarMp3Renamer.kt](car-mp3-renamer/src/main/kotlin/com/clarkjohn/mp3/carrenamer/CarMp3Renamer.kt) or with Maven, run `mvn clean` on the project 

