package com.example.notesapp.data


import android.content.Context

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val noteRepository: NoteRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineItemsRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [ItemsRepository]
     */
    override val noteRepository: NoteRepository by lazy {
        NoteOfflineRepository(NoteDatabase.getDatabase(context).noteDao())
    }

}