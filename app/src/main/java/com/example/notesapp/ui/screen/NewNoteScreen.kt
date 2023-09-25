package com.example.notesapp.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                  viewModel: HomeScreenViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    NewNoteBody()
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewNoteBody() {
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
                       Icon(Icons.Filled.Edit, "Localized description")
                   }
               }
           )
       }){
       innerpadding->
           Column (modifier = Modifier.padding(innerpadding)){
               Text(
                   text = "Titre beuwqhfwsiaughfioasduhasidfuyagdfiu agsukfafjuasdskfgayfgsadkufaydgfakdfyzsgk",
                   fontWeight = FontWeight.Bold,
                   fontSize = 20.sp,
                   modifier = Modifier.padding(5.dp),
               )

               Divider(color = Color.Gray, thickness = 1.dp)
               Text(text = "Content")
           }

   }

}



@Preview
@Composable
fun NewNoteBodyPreview() {
    NewNoteBody()

}
