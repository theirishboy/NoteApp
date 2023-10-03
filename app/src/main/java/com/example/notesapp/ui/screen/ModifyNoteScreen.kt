package com.example.notesapp.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notesapp.R
import com.example.notesapp.ui.AppViewModelProvider
import com.example.notesapp.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

object ModifyNoteDestination : NavigationDestination {
    override val route = "ModifyNote"
    override val titleRes = R.string.app_name
    const val noteIdArg = "noteId"
    val routeWithArgs = "$route/{$noteIdArg}"

}
@Composable
fun ModifyNoteScreen(
    navigateToNewNote: () -> Unit = {},
    navigateBack: () -> Unit = {},
    modifyNoteViewModel: ModifyNoteViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    ModifyNoteBody(modifyNoteViewModel,navigateBack,navigateToNewNote)


}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModifyNoteBody(
    modifyNoteViewModel: ModifyNoteViewModel,
    navigateBack: () -> Unit,
    navigateToNewNote: () -> Unit
) {
    val localFocus = LocalFocusManager.current
    val uiState by modifyNoteViewModel.modifyNoteUiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold (topBar = { ModifyNoteTopBar(navigateBack) },
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(onClick = navigateToNewNote) {
                        Icon(
                            Icons.Filled.Create,
                            contentDescription = "Localized description",
                        )
                    }
                    IconButton(onClick = {

                        coroutineScope.launch {
                            modifyNoteViewModel.deleteNote()
                            navigateBack()
                        }}) {
                        Icon(
                            Icons.Filled.Delete,
                            contentDescription = "Localized description",
                        )
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {modifyNoteViewModel.modifyNote(note = uiState!!.note)
                                  navigateBack()},
                        containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                    ) {
                        Icon(painter = painterResource(id = R.drawable.baseline_save_24), "Save Button")
                    }
                }
            )
        }){
            innerpadding->
        ModifyNoteTexts(Modifier.padding(paddingValues = innerpadding), uiState, modifyNoteViewModel, localFocus)

    }

}

@Composable
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
private fun ModifyNoteTexts(
    modifier: Modifier,
    uiState: ModifyNoteUiState?,
    modifyNoteViewModel: ModifyNoteViewModel,
    localFocus: FocusManager,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(modifier = modifier) {
        TextField(
            value = uiState!!.note.title,
            onValueChange = { title -> modifyNoteViewModel.updateTitle(title) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Title") },
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
            value = uiState.note.content,
            onValueChange = { content -> modifyNoteViewModel.updateContent(content) },
            modifier = Modifier.fillMaxSize(),
            label = { Text(text = "Content") },
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


@Preview
@Composable
fun ModifyNoteBodyPreview() {
    //ModifyNoteBody(NewNoteUiState())

}
