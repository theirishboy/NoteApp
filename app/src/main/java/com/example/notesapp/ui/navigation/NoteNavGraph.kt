package com.example.notesapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.notesapp.ui.screen.HomeDestination
import com.example.notesapp.ui.screen.HomeScreen
import com.example.notesapp.ui.screen.NewNoteDestination
import com.example.notesapp.ui.screen.NewNoteScreen

@Composable
fun NoteNavHost(navController: NavHostController,
                modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToNewNote = {navController.navigate(NewNoteDestination.route)  },

            )
        }
        composable(route = NewNoteDestination.route) {
            NewNoteScreen(
                navigateToNewNote = {navController.navigate(NewNoteDestination.route)  },
                navigateBack = {navController.popBackStack()},

            )
        }
    }


}