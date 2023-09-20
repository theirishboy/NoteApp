package com.example.notesapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.notesapp.ui.navigation.NoteNavHost

@Composable
fun NoteApp(navController: NavHostController = rememberNavController()) {
    NoteNavHost(navController = navController)
}


