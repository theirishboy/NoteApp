package com.example.notesapp.ui.screen

import android.service.autofill.OnClickAction
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.notesapp.R
import com.example.notesapp.data.Note
import com.example.notesapp.ui.navigation.NavigationDestination
import com.example.notesapp.ui.theme.NotesAppTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notesapp.ui.AppViewModelProvider

object HomeDestination : NavigationDestination {
    override val route = "home"
    const val ArgumentItemId = "itemId"
    override val titleRes = R.string.app_name
}
@Composable
fun HomeScreen(
    navigateToNewNote: () -> Unit,
    navigateToModifyNote: (Int) -> Unit,
    viewModel: HomeScreenViewModel = viewModel(factory = AppViewModelProvider.Factory)) {
    HomeScreenBody(viewModel,navigateToNewNote, navigateToModifyNote)
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreenBody(
    viewModel: HomeScreenViewModel,
    navigateToNewNote: () -> Unit,
    navigateToModifyNote: (Int) -> Unit, ) {
    val notesValue = viewModel.homeScreenUiState.collectAsStateWithLifecycle()
    val notesList = notesValue.value.myNotes
    var contextNoteId by rememberSaveable { mutableStateOf<Int?>(null) }
    val haptics = LocalHapticFeedback.current


    Scaffold(topBar = { HomeScreenTopBar() },
    floatingActionButton = {
        FloatingActionButton(onClick = navigateToNewNote ) {
            Icon(Icons.Default.Edit, contentDescription = "add new note")
        }
    }
    ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding),

                ) {
                items(notesList)
                {
                    OneNote(
                        title = it.title,
                        content = it.content,
                        date = LocalDateTime.now(),
                        modifier =  Modifier
                            .combinedClickable(
                                onClick = { navigateToModifyNote(it.id) },
                                onLongClick = {
                                    haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                                    contextNoteId = it.id
                                },
                                onLongClickLabel = "LongClick"
                            )
                    )
                }
            }
            if (contextNoteId != null && notesList.any{it.id == contextNoteId}) {
                NoteActionSheet(
                    note = notesList.first { it.id == contextNoteId },
                    onDismissSheet = { contextNoteId = null },
                    onDeleteIcon = { viewModel.deleteNote(notesList.first { it.id == contextNoteId })}
                )
            }
        }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteActionSheet(note: Note, onDismissSheet: () -> Unit, onDeleteIcon: () -> Unit) {
    ModalBottomSheet(onDismissRequest = onDismissSheet){
        ListItem(
            headlineContent = { Text("Delete note ${note.title}") },
            leadingContent = { Icon(Icons.Default.Delete, null)},
            modifier = Modifier.clickable(onClick = onDeleteIcon)
        )
    }
}


@Composable
fun OneNote(
    modifier: Modifier = Modifier,
    title: String,
    content: String = "",
    date: LocalDateTime,
)
{
    val paddingModifier = Modifier.padding(2.dp)
    Card(shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(5.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Text(text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp ,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = paddingModifier)
        Text(text = date.format(DateTimeFormatter.ofPattern("dd/MM")).toString()+ " " +  content,
            maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = paddingModifier)
    }
}
@Preview(showBackground = true)
@Composable
fun OneNotePreview() {
    NotesAppTheme {
        val noteTest = Note(title = "Preview title",
            content = "Preview Content zorhp ovevoevhlquhvflvuv fuhvq duvh",
            dateModification = LocalDateTime.now())
        OneNote(
            title = noteTest.title,
            content = noteTest.content,
            date = noteTest.dateModification,
        )
    }
}
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    NotesAppTheme {
        HomeScreen(navigateToNewNote = {}, navigateToModifyNote = {})
    }
}