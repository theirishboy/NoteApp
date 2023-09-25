package com.example.notesapp.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CreateSportSession(){
    val items = listOf("Exercises","Workout","Profile")
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "SportSession",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Return to sport session"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Text(text = "EDIT",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            )
        },
        bottomBar = { Button(onClick = {}, modifier = Modifier
            .fillMaxWidth()
            .padding(start = 3.dp, end = 3.dp, bottom = 5.dp, top = 5.dp)) { Text(text =  "StartWorkout") } }
    ){innerPadding ->
        Column {

            ExercisesList(innerPadding)

        }
    }
}
@Composable
fun ExercisesList(innerPadding: PaddingValues) {
    LazyColumn( modifier = Modifier.fillMaxSize(),
        contentPadding = innerPadding,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        items(count = 5 ){
            ExerciseShortcut()
            if(it == 4){
                addExercise()
            }

        }
    }

}
@Composable
private fun addExercise() {
    Button(onClick = { /* Do something! */ },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 3.dp, end = 3.dp, bottom = 5.dp))
    { Text("add exercise") }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseShortcut() {
    val cardData = listOf(
        Pair(1, 10),
        Pair(2, 20),
        Pair(3, 30),
        Pair(4, 40),
        Pair(5, 150)
    )

    Surface(onClick = { /* TODO */ }, shape = MaterialTheme.shapes.large)
    {

        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),

            ) {
            Icon(
                Icons.Rounded.Person,
                contentDescription = "icon of sport",
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Icon(
                Icons.Rounded.Person,
                contentDescription = "icon of sport",
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Column(verticalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.weight(0.7f)) {
                Row {
                    Text(
                        text = "Exercice 1",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold ,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
                Row {
                    LazyRow(
                        // modifier = Modifier.fillMaxSize(),
                        content = {
                            items(cardData) { (param1, param2) ->

                                Card(modifier = Modifier
                                    .fillMaxWidth(0.2f)
                                    .padding(3.dp)) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(5.dp),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ){
                                        Text(modifier = Modifier.padding(2.dp), text = param1.toString())
                                        Divider(thickness = 1.dp, color = Color.Gray, modifier = Modifier.width(25.dp))
                                        Text(modifier = Modifier.padding(2.dp), text = param2.toString())
                                    }
                                }
                            }
                        })


                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.End){
                        Text(
                            modifier = Modifier.fillMaxSize(),
                            text = "rest : 60s",
                            //textAlign = TextAlign.End,
                            style = MaterialTheme.typography.bodyMedium,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1)
                    }
                }


            }




        }
    }

}
@Preview
@Composable
fun CreateSportSessionPreview(){
    CreateSportSession()
}