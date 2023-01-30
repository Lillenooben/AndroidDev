package com.lije21dg.todo2

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun TaskScreen(navController: NavHostController) {
    var tasks = testTaskList
    println("Loading Task Screen")
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ){
        if(tasks.isEmpty()){
            item{
                Text("List is Empty",
                    modifier = Modifier
                )
            }
        }
        items(tasks){task ->
            println("Printing Task : $task")
            Card(
                shape = RoundedCornerShape(5.dp),
                border = BorderStroke(2.dp, Color.Gray),
                elevation = 10.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    .clickable{
                        navController.navigate("editScreen/${task.taskId}")
                    }
            ){
                Row(
                    horizontalArrangement = Arrangement.SpaceAround
                ){
                    Text(task.taskTitle,
                        modifier = Modifier
                            .padding(4.dp)
                    )
                }

            }

        }
    }
}