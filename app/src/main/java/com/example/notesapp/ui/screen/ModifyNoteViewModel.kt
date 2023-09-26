package com.example.notesapp.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.data.Note
import com.example.notesapp.data.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ModifyNoteViewModel(private val noteRepository: NoteRepository): ViewModel() {

    private val _eventHandling =
        MutableStateFlow<EventHandling<String>>(EventHandling.OnSuccess("init"))
    val eventHandling : StateFlow<EventHandling<String>> = _eventHandling
    fun deleteNote(note: Note){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                try {
                    noteRepository.deleteNote(note)

                    _eventHandling.value = EventHandling.OnSuccess("Operation Succeeded")
                }catch (e : Exception){
                    _eventHandling.value = EventHandling.OnFailure("Operation failed")

                }
            }
        }
    }
    fun modifyNote(note: Note){
        viewModelScope.launch{
            withContext(Dispatchers.IO){
                try {
                    noteRepository.modifyNote(note)
                    _eventHandling.value = EventHandling.OnSuccess("Operation Succeeded")
                }catch (e : Exception){
                    _eventHandling.value = EventHandling.OnFailure("Operation failed")

                }
            }
        }
    }

}
sealed class EventHandling<out T>{
    data class OnSuccess<out T>(val data: T) : EventHandling<T>()
    data class OnFailure(val errorMessage: String) : EventHandling<Nothing>()
}