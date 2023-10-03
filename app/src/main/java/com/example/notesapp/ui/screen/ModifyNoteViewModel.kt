package com.example.notesapp.ui.screen

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.data.Note
import com.example.notesapp.data.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class ModifyNoteUiState(var note : Note = Note(title = "title", content = "content"))
class ModifyNoteViewModel(savedStateHandle: SavedStateHandle,
                          private val noteRepository: NoteRepository): ViewModel() {

    private var _eventHandling = MutableStateFlow<EventHandling<String>>(EventHandling.OnInit("init"))
    val eventHandling : StateFlow<EventHandling<String>> = _eventHandling

    private val noteId: Int = checkNotNull(savedStateHandle[ModifyNoteDestination.noteIdArg])

    var _modifyNoteUiState: MutableStateFlow<ModifyNoteUiState> = MutableStateFlow(ModifyNoteUiState())
    val modifyNoteUiState : StateFlow<ModifyNoteUiState> = _modifyNoteUiState
    init {
        viewModelScope.launch {
                try {
                    noteRepository.selectNoteById(noteId).collect{
                        _modifyNoteUiState.value = ModifyNoteUiState(it)
                    }
                    _eventHandling.value = EventHandling.OnSuccessLoadingNote("Operation Succeeded")
                }catch (nullException : NullPointerException){
                    _eventHandling.value = EventHandling.OnErrorId("Need to move on")
                    Log.e("ModifyNoteViewModel", "Error retrieving note", nullException)

                }
                catch (e : Exception){
                    _eventHandling.value = EventHandling.OnFailure("Operation retrieve note failed with $e")
                    Log.e("ModifyNoteViewModel", "Error retrieving note", e)

                }
        }
    }


    //    fun getNoteById(id: Int){
//        viewModelScope.launch {
//            withContext(Dispatchers.IO){
//                try {
//                    noteRepository.selectNoteById(id).collect{
//                        _modifyNoteUiState.update { item -> item.copy(note = it) }
//                        _eventHandling.value = EventHandling.OnSuccessLoadingNote("Operation Succeeded")
//
//                    }
//                }catch (nullException : NullPointerException){
//                    _eventHandling.value = EventHandling.OnErrorId("Need to move on")
//                     Log.e("ModifyNoteViewModel", "Error retrieving note", nullException)
//
//                }
//                catch (e : Exception){
//                    _eventHandling.value = EventHandling.OnFailure("Operation retrieve note failed with $e")
//                     Log.e("ModifyNoteViewModel", "Error retrieving note", e)
//
//                }
//            }
//        }
//    }
    fun deleteNote(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                try {
                    noteRepository.deleteNote(_modifyNoteUiState.value.note)
                    _eventHandling.value = EventHandling.OnSuccessModifyOrDeleteNote("Operation Succeeded")
                }catch (e : Exception){
                    _eventHandling.value = EventHandling.OnFailure("Operation delete failed  with $e")
                    Log.e("ModifyNoteViewModel", "Error deleting note", e)

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
                    Log.e("ModifyNoteViewModel", "Error modifying note", e)

                }
            }
        }
    }
    fun updateTitle(title: String) {
        _modifyNoteUiState.value = _modifyNoteUiState.value.copy(note = _modifyNoteUiState.value.note.copy(title = title))
    }

    fun updateContent(content: String) {
        _modifyNoteUiState.value = _modifyNoteUiState.value.copy(note = _modifyNoteUiState.value.note.copy(content = content))
    }

}
sealed class EventHandling<out T>{
    data class OnInit<out T>(val messageInit: String): EventHandling<T>()
    data class OnErrorId<out T>(val errorMessage: String): EventHandling<T>()
    data class OnSuccessModifyOrDeleteNote<out T>(val data: T) : EventHandling<T>()
    data class OnSuccessLoadingNote<out T>(val data: T) : EventHandling<T>()
    data class OnFailure(val errorMessage: String) : EventHandling<String>()
}