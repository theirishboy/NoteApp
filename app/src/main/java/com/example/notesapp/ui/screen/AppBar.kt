package com.example.notesapp.ui.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenTopBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                "Notes",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Localized description"
                )
            }
        },
        actions = {
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Localized description"
                )
            }
        }
    )

}
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun NewNoteTopBar() {
    CenterAlignedTopAppBar(title = {
        Text(text = "New Note",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp)},
        navigationIcon = {
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Localized description"
                )
            }
        },






    )
}

@Composable
fun NewNoteFloatingButton(onClickAction: () -> Unit) {
    FloatingActionButton(onClick = { onClickAction }) {
        Icon(Icons.Default.Edit, contentDescription = "add new note")
    }
}

@Composable
@Preview
fun HomeScreenTopAppBarPreview(){
    HomeScreenTopBar()
}
@Composable
@Preview
fun CreateNoteTopAppBarPreview(){
    NewNoteTopBar()
}
@Composable
@Preview
fun FloatingButtonPreview(){
    //NewNoteFloatingButton()
}
