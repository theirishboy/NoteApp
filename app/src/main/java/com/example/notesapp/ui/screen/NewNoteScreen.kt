package com.example.notesapp.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notesapp.R
import com.example.notesapp.ui.AppViewModelProvider
import com.example.notesapp.ui.navigation.NavigationDestination

object NewNoteDestination : NavigationDestination {
    override val route = "NewNote"
    override val titleRes = R.string.app_name
}
@Composable
fun NewNoteScreen(navigateToNewNote: () -> Unit,
                  navigateBack: () -> Unit,
                  viewModel: NewNoteViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val newNoteUiState = viewModel._newNoteUiState.collectAsState()

    NewNoteBody(newNoteUiState.value)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewNoteBody(newNoteUiState: NewNoteUiState) {
   Scaffold (topBar = { NewNoteTopBar() },
       bottomBar = {
           BottomAppBar(
               actions = {
                   IconButton(onClick = { /* doSomething() */ }) {
                       Icon(Icons.Filled.Check, contentDescription = "Localized description")
                   }
                   IconButton(onClick = { /* doSomething() */ }) {
                       Icon(
                           Icons.Filled.Edit,
                           contentDescription = "Localized description",
                       )
                   }
               },
               floatingActionButton = {
                   FloatingActionButton(
                       onClick = { /* do something */ },
                       containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                       elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                   ) {
                       Icon(painter = painterResource(id = R.drawable.baseline_save_24), "Save Button")
                   }
               }
           )
       }){
       innerpadding->
           Column (modifier = Modifier.padding(innerpadding)){
              TextField(
                   value = "Test",
                   onValueChange = {},
                   modifier = Modifier.fillMaxWidth(),
                   label = { Text(text = "Title")},
                   keyboardOptions = KeyboardOptions.Default.copy(
                       imeAction = ImeAction.Done
                   ),
                   keyboardActions = KeyboardActions(
                       onDone = {}
                   ),
                  shape = RectangleShape
               )

               TextField(
                   value = "Content",
                   onValueChange = {},
                   modifier = Modifier.fillMaxSize(),
                   label = { Text(text = "Title")},
                   keyboardOptions = KeyboardOptions.Default.copy(
                       imeAction = ImeAction.Done
                   ),
                   keyboardActions = KeyboardActions(
                       onDone = {}
                   )
               )
           }

   }

}



@Preview
@Composable
fun NewNoteBodyPreview() {
    NewNoteBody(NewNoteUiState())

}
