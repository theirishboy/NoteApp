package com.example.notesapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.notesapp.ui.screen.HomeDestination
import com.example.notesapp.ui.screen.HomeScreen
import com.example.notesapp.ui.screen.ModifyNoteDestination
import com.example.notesapp.ui.screen.ModifyNoteScreen
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
                navigateToNewNote = {navController.navigate(NewNoteDestination.route)},
                navigateToModifyNote = { noteId -> navController.navigate("${ModifyNoteDestination.route}/$noteId")}

            )
        }
        composable(route = NewNoteDestination.route) {
            NewNoteScreen(
                navigateToHomeScreen = {navController.navigate(HomeDestination.route)  },
                navigateBack = {navController.popBackStack()},

                )
        }
        composable(route = ModifyNoteDestination.routeWithArgs,
            arguments = listOf(navArgument(ModifyNoteDestination.noteIdArg){
                type = NavType.IntType
            })
        ) {
            ModifyNoteScreen(
                    navigateToNewNote = {navController.navigate(NewNoteDestination.route)  },
                    navigateBack = {navController.popBackStack()},
                )


        }
    }


}