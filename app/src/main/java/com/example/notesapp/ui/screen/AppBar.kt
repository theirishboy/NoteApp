package com.example.notesapp.ui.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
fun CustomisedTopBar() {
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
fun CustomisedFloatingButton() {
    FloatingActionButton(onClick = { }) {
        Icon(Icons.Default.Add, contentDescription = "add new note")
    }
}

@Composable
@Preview
fun TopAppBarPreview(){
    CustomisedTopBar()
}
@Composable
@Preview
fun FloatingButtonPreview(){
    CustomisedFloatingButton()
}