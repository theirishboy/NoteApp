package com.example.notesapp.data

import kotlinx.coroutines.flow.Flow

class NoteOfflineRepository(private val noteDao: NoteDao) : NoteRepository {
    override fun getAllNotes(): Flow<List<Note>> = noteDao.getAllNotes()
    override fun insertNotes(note: Note) = noteDao.insertNote(note)

}