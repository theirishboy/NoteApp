package com.example.notesapp.ui.screen

import androidx.compose.runtime.MutableState
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.notesapp.data.Note
import com.example.notesapp.data.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class NewNoteUiState(val title:String = "", val content : String = "")
class NewNoteViewModel( noteRepository: NoteRepository) : ViewModel() {
    val _newNoteUiState = MutableStateFlow(NewNoteUiState())
    val uiState: StateFlow<NewNoteUiState> = _newNoteUiState.asStateFlow()

}