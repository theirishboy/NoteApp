package com.example.notesapp.data

import kotlinx.coroutines.flow.Flow

interface NoteRepository  {
    fun getAllNotes(): Flow<List<Note>>

    /**
     * Retrieve an Note from the given data source that matches with the [id].
     */
}