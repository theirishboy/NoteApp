package com.example.notesapp.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.data.Note
import com.example.notesapp.data.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.NullPointerException

data class ModifyNoteUiState(var note : Note)
class ModifyNoteViewModel(private val noteRepository: NoteRepository): ViewModel() {

    private var _modifyNoteUiState = MutableStateFlow(ModifyNoteUiState(Note(title = "loading", content = "")))
    val modifyNoteHandling : StateFlow<ModifyNoteUiState?> = _modifyNoteUiState

    private val _eventHandling =
        MutableStateFlow<EventHandling<String>>(EventHandling.OnInit("init"))
    val eventHandling : StateFlow<EventHandling<String>> = _eventHandling
    fun getNoteById(id: Int){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                try {
                    noteRepository.selectNoteById(id).collect{
                        _modifyNoteUiState.update { item -> item.copy(note = it) }
                        _eventHandling.value = EventHandling.OnSuccessLoadingNote("Operation Succeeded")

                    }
                }catch (nullException : NullPointerException){
                    _eventHandling.value = EventHandling.OnErrorId("Need to move on")
                    println("id is null so we go back to main screen")
                }
                catch (e : Exception){
                    _eventHandling.value = EventHandling.OnFailure("Operation retrieve note failed with $e")

                }
            }
        }
    }
    fun deleteNote(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                try {
                    noteRepository.deleteNote(_modifyNoteUiState.value.note)
                    _eventHandling.value = EventHandling.OnSuccessModifyOrDeleteNote("Operation Succeeded")
                }catch (e : Exception){
                    _eventHandling.value = EventHandling.OnFailure("Operation delete failed  with $e")

                }
            }
        }
    }
    fun modifyNote(note: Note){
        viewModelScope.launch{
            withContext(Dispatchers.IO){
                try {
                    noteRepository.modifyNote(note)
                    _eventHandling.value = EventHandling.OnSuccessModifyOrDeleteNote("Operation Succeeded")
                }catch (e : Exception){
                    _eventHandling.value = EventHandling.OnFailure("Operation modification failed  with $e")

                }
            }
        }
    }
    fun updateTitle(title : String){
        _modifyNoteUiState.update { item -> item.copy(note = Note(id = item.note.id,title = title,
            content = item.note.content,
            dateModification = item.note.dateModification
            )) }
    }
    fun updateContent(content : String){
        _modifyNoteUiState.update { item -> item.copy(note = Note(id = item.note.id,title = item.note.title,
            content = content,
            dateModification = item.note.dateModification
            )) }
    }

}
sealed class EventHandling<out T>{
    data class OnInit<out T>(val messageInit: String): EventHandling<T>()
    data class OnErrorId<out T>(val errorMessage: String): EventHandling<T>()
    data class OnSuccessModifyOrDeleteNote<out T>(val data: T) : EventHandling<T>()
    data class OnSuccessLoadingNote<out T>(val data: T) : EventHandling<T>()
    data class OnFailure(val errorMessage: String) : EventHandling<String>()
}