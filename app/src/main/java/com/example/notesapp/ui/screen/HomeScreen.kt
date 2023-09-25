package com.example.notesapp.ui.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    override val titleRes = R.string.app_name
}
@Composable
fun HomeScreen(navigateToNewNote: () -> Unit,
               viewModel: HomeScreenViewModel = viewModel(factory = AppViewModelProvider.Factory)) {
    HomeScreenBody(viewModel,navigateToNewNote)
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenBody(viewModel: HomeScreenViewModel, navigateToNewNote: () -> Unit, ) {
    val noteTest = Note(title = "Preview title",
        content = "Preview Content zorhp ovevoevhlquhvflvuv fuhvq duvh",
        dateModification = LocalDateTime.now())
    val notesList = viewModel._homeScreenUiState.collectAsState().value.myNotes
    Scaffold(topBar = { HomeScreenTopBar() },
    floatingActionButton = {
        FloatingActionButton(onClick =  navigateToNewNote ) {
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
                OneNote(title = it.title, content= it.content, date = LocalDateTime.now())
            }
        }

        
    }
}


@Composable
fun OneNote(title : String, content : String = "", date : LocalDateTime )
{
    val paddingModifier = Modifier.padding(2.dp)
    Card(shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(5.dp),
        modifier = Modifier
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
        OneNote(noteTest.title,noteTest.content,noteTest.dateModification)
    }
}
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    NotesAppTheme {
        HomeScreen(navigateToNewNote = {})
    }
}