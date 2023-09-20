package com.example.notesapp.ui.screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.data.Note
import com.example.notesapp.data.NoteRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class HomeScreenUiState(val myNotes: List<Note> = listOf())
class HomeScreenViewModel( noteRepository: NoteRepository) : ViewModel() {

    val _homeScreenUiState : StateFlow<HomeScreenUiState> =
        noteRepository.getAllNotes()
            .filterNotNull()
            .map {
                HomeScreenUiState(it)
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(500),
                initialValue = HomeScreenUiState()
            )

}