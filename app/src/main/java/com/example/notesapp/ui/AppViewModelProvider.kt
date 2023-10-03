package com.example.notesapp.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.notesapp.NoteApplication
import com.example.notesapp.ui.screen.HomeScreenViewModel
import com.example.notesapp.ui.screen.ModifyNoteViewModel
import com.example.notesapp.ui.screen.NewNoteScreen
import com.example.notesapp.ui.screen.NewNoteViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for ItemEditViewModel
        initializer {
            HomeScreenViewModel(
                noteApplication().container.noteRepository
            )
        }
        initializer {
            NewNoteViewModel(
                noteApplication().container.noteRepository
            )
        }
        initializer {
            ModifyNoteViewModel(
                this.createSavedStateHandle(),
                noteApplication().container.noteRepository
            )
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [InventoryApplication].
 */
fun CreationExtras.noteApplication(): NoteApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as NoteApplication)