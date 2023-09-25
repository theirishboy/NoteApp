package com.example.notesapp.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.hamcrest.CoreMatchers.hasItem
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.concurrent.CountDownLatch

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
    fun testInsertNoteReturnTrue() = runBlocking  {
        val note = Note(2,"note demo","Ceci est une note de demo")
        noteDao.insertNote(note)
        val latch = CountDownLatch(1)
        val job = async(Dispatchers.IO) {
            noteDao.getAllNotes().collect {
                assertThat(it, hasItem(note))
                latch.countDown()

            }
        }
        withContext(Dispatchers.IO) {
            latch.await()
        }
        job.cancelAndJoin()
    }
    @Test
    @Throws(Exception::class)
    fun testSelectAllNote() = runBlocking {
        noteDao.deleteAll()
        val allNoteToTest : List<Note> = listOf(
            Note(1,"note demo autre","Ceci est une  autre note de demo"),
            Note(2,"note demo","Ceci est une note de demo"),
            Note(3,"note demo autre encore","Ceci est encore une  autre note de demo"))

        for (element in allNoteToTest) noteDao.insertNote(element)
        val job = async(Dispatchers.IO) {
            noteDao.getAllNotes().collect {
                assertThat(it, hasItem(allNoteToTest))

            }

        }
        job.cancelAndJoin()

    }
    @Test
    @Throws(Exception::class)
    fun testDeleteNoteById() = runBlocking {
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
        val job = async(Dispatchers.IO) {
            noteDao.getAllNotes().collect {
                assertThat(it, hasItem(resultExceptedWithDeleteElement))

            }

        }
        job.cancelAndJoin()

    }
}