package com.example.notesapp.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notesapp.R
import com.example.notesapp.data.Note
import com.example.notesapp.ui.AppViewModelProvider
import com.example.notesapp.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

object NewNoteDestination : NavigationDestination {
    override val route = "NewNote"
    override val titleRes = R.string.app_name
}
@Composable
fun NewNoteScreen(navigateToHomeScreen: () -> Unit,
                  navigateBack: () -> Unit,
                  newNoteViewModel: NewNoteViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {

    NewNoteBody(newNoteViewModel,navigateBack,navigateToHomeScreen)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NewNoteBody(
    newNoteViewModel: NewNoteViewModel,
    navigateBack: () -> Unit,
    navigateToHomeScreen: () -> Unit
) {
    var title by  remember { mutableStateOf("") }
    var content by remember{ mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val localFocus = LocalFocusManager.current
    val saveResult by newNoteViewModel.saveResult.collectAsState()
    when (saveResult) {
            is SaveResult.Success -> {
                (saveResult as SaveResult.Success).data
        }
            is SaveResult.Failure -> {
            val errorMessage = (saveResult as SaveResult.Failure).errorMessage
            Text(text = "Error: $errorMessage", color = Color.Red)
        }

        else -> {}
    }
val coroutineScope = rememberCoroutineScope()
    Scaffold (topBar = { NewNoteTopBar(navigateBack) },
       bottomBar = {
           BottomAppBar(
               actions = {
                   IconButton(onClick = { title = "";content = ""}) {
                       Icon(
                           Icons.Filled.Refresh,
                           contentDescription = "Localized description",
                       )
                   }
               },
               floatingActionButton = {
                   FloatingActionButton(
                       onClick = {

                           coroutineScope.launch {
                           newNoteViewModel.addNewNote(Note(title = title, content = content))
                           navigateToHomeScreen()
                           }},
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
                   value = title,
                   onValueChange = { title = it},
                   modifier = Modifier.fillMaxWidth(),
                   label = { Text(text = "Title")},
                   keyboardOptions = KeyboardOptions.Default.copy(
                       imeAction = ImeAction.Done
                   ),
                   keyboardActions = KeyboardActions(
                       onDone = {
                           localFocus.clearFocus()
                           keyboardController?.hide()
                       }
                   ),
                  shape = RectangleShape
               )

               TextField(
                   value = content,
                   onValueChange = {content = it},
                   modifier = Modifier.fillMaxSize(),
                   label = { Text(text = "Content")},
                   keyboardOptions = KeyboardOptions.Default.copy(
                       imeAction = ImeAction.Done
                   ),
                   keyboardActions = KeyboardActions(
                       onDone = {
                           localFocus.clearFocus()
                           keyboardController?.hide()
                       }
                   ),
               )
           }

   }

}



@Preview
@Composable
fun NewNoteBodyPreview() {
    //NewNoteBody(NewNoteUiState())

}
