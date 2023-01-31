package com.lije21dg.todo2

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController

@Composable
fun ButtonBar(navController: NavHostController){
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Gray)
    ){

        Button(onClick = {
            testTaskList.add(TaskItem(dummyTitle, dummyText, testTaskList.size))
        }) {
            Text("Add Dummy")
        }

        Button(onClick = { navController.navigate("taskScreen") {
            popUpTo("taskScreen"){
                inclusive = true
            }
        } }) {
            Text("Home")
        }

        Button(onClick = { navController.navigate("createScreen"){
            launchSingleTop = true
            popUpTo("taskScreen")
        } }) {
            Text("Add Task")
        }
    }
}