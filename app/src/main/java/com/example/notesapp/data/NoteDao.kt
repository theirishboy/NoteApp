package com.example.notesapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert
    fun insertNote(note: Note)

    @Query("DELETE FROM NOTES")
    fun deleteAll()

    @Query("DELETE FROM NOTES WHERE id = :id")
    fun deleteById(id : Int)

    @Query("SELECT * FROM NOTES")
    fun getAllNotes(): Flow<List<Note>>

    @Query("SELECT * from NOTES WHERE id = :id")
    fun findNoteById(id : Int) : Note

}