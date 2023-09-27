package com.example.notesapp.ui.screen

import androidx.compose.runtime.MutableState
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.data.Note
import com.example.notesapp.data.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewNoteViewModel(private val noteRepository: NoteRepository) : ViewModel() {
    private val _saveResult =
        MutableStateFlow<SaveResult<String>>(SaveResult.Init("wait"))
    val saveResult: StateFlow<SaveResult<String>> = _saveResult

    fun addNewNote(note: Note){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    noteRepository.insertNotes(Note(title = note.title, content = note.content))
                    _saveResult.value = SaveResult.Success("Operation succeeded!")

                }catch (e : Exception){
                    _saveResult.value = SaveResult.Failure("Operation failed!")

                }

            }
        }
    }

}
sealed class SaveResult<out T> {
    data class Success<out T>(val data: T) : SaveResult<T>()
    data class Failure(val errorMessage: String) : SaveResult<Nothing>()
    data class Init(val infoMessage: String) : SaveResult<Nothing>()
}