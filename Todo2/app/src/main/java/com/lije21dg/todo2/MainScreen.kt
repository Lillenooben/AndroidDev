package com.lije21dg.todo2

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreen() {

    val navController = rememberNavController()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red)
            .padding(8.dp)
    ){

        NavHost(
            navController,
            "taskScreen",
            modifier = Modifier.weight(1f)){
            composable("taskScreen"){
                TaskScreen(navController)
            }
            composable("createScreen")
            {
                CreateScreen()
            }
            composable("editScreen/{id}"){
                val id = it.arguments!!.getString("id")!!.toInt()
                if(id < testTaskList.size){
                    EditScreen(testTaskList[id], navController)
                }
            }
        }
        ButtonBar(navController = navController)
    }
}