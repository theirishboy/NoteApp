package com.example.notesapp.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.Flow
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class NoteDaoTest {
    private lateinit var noteDao: NoteDao
    private lateinit var db: NoteDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, NoteDatabase::class.java).build()
        noteDao = db.noteDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun testSelectNoteById() {
        val note = Note(2,"note demo","Ceci est une note de demo")
        noteDao.insertNote(note)
        val findNoteById = noteDao.findNoteById(2)

        assertEquals(note, findNoteById)
    }
    @Test
    @Throws(Exception::class)
    fun testSelectAllNote() {
        noteDao.deleteAll()
        val allNoteToTest : List<Note> = listOf(
            Note(1,"note demo autre","Ceci est une  autre note de demo"),
            Note(2,"note demo","Ceci est une note de demo"),
            Note(3,"note demo autre encore","Ceci est encore une  autre note de demo"))

        for (element in allNoteToTest) noteDao.insertNote(element)
        val allNoteRetrieve : Flow<List<Note>> = noteDao.getAllNotes()

        assertEquals(allNoteToTest, allNoteRetrieve)
    }
    @Test
    @Throws(Exception::class)
    fun testDeleteNoteById() {
        noteDao.deleteAll()
        val allNoteToTest : List<Note> = listOf(
            Note(1,"note demo autre","Ceci est une  autre note de demo"),
            Note(2,"note demo","Ceci est une note de demo"),
            Note(3,"note demo autre encore","Ceci est encore une  autre note de demo"))
        val resultExceptedWithDeleteElement : List<Note> = listOf(
            Note(2,"note demo","Ceci est une note de demo"),
            Note(3,"note demo autre encore","Ceci est encore une  autre note de demo"))

        for (element in allNoteToTest) noteDao.insertNote(element)

        noteDao.deleteById(1)


        val allNoteRetrieve : List<Note>  = noteDao.getAllNotes()

        assertEquals(resultExceptedWithDeleteElement, allNoteRetrieve)
    }
//    @Test
//    @Throws(Exception::class)
//    fun createNote() {
//        val note: Note = Note(1,"note demo","Ceci est une note de demo")
//        noteDao.insert(note)
//        val byName = noteDao.findnotesByName("george")
//        assertThat(byName.get(0), equalTo(note))
//    }
}