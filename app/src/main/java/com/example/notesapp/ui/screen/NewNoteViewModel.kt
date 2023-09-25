package com.example.notesapp.ui.screen

import androidx.lifecycle.ViewModel
import com.example.notesapp.data.Note
import com.example.notesapp.data.NoteRepository

data class NewNoteUiState(val title:String = "", val content : String = "")
class NewNoteViewModel( noteRepository: NoteRepository) : ViewModel() {


}