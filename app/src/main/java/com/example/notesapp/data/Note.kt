package com.example.notesapp.data

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity(tableName = "Notes")

data class Note(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val title : String,
    val content : String,
    val dateModification : LocalDateTime = LocalDateTime.now()
): Serializable {

    val createdDateFormatted: String
        get() = dateModification.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
}