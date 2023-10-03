package com.example.notesapp.ui.screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.data.Note
import com.example.notesapp.data.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class HomeScreenUiState(val myNotes: List<Note> = listOf())
class HomeScreenViewModel( private val noteRepository: NoteRepository) : ViewModel() {

    private val _homeScreenUiState: MutableStateFlow<HomeScreenUiState> =
        MutableStateFlow(HomeScreenUiState())

    val homeScreenUiState: StateFlow<HomeScreenUiState> = _homeScreenUiState
    init {
        viewModelScope.launch {
            noteRepository.getAllNotes()
                .filterNotNull()
                .map {
                    HomeScreenUiState(it)
                }
                .collect {
                    _homeScreenUiState.value = it
                }
        }
    }
    fun deleteNote(note : Note){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                 noteRepository.deleteNote(note)
            }
        }

    }

}