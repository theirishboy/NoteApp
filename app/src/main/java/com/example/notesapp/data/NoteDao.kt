package com.example.notesapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert
    fun insertNote(note: Note)

    @Query("DELETE FROM NOTES")
    fun deleteAll()

    @Delete
    fun deleteNote(note: Note)

    @Query("SELECT * FROM NOTES")
    fun getAllNotes(): Flow<List<Note>>

    @Query("SELECT * from NOTES WHERE id = :id")
    fun findNoteById(id : Int) : Flow<Note>

    @Update
    fun modifyNote(note: Note)

}