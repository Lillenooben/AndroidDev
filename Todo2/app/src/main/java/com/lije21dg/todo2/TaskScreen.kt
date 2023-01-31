package com.lije21dg.todo2

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun TaskScreen(navController: NavHostController) {
    var tasks = testTaskList
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        if(tasks.isEmpty()){
            item{
                Text("List is Empty",
                    modifier = Modifier
                )
            }
        }
        items(tasks){task ->
            Card(
                shape = RoundedCornerShape(5.dp),
                border = BorderStroke(2.dp, Color.Gray),
                elevation = 10.dp,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(4.dp)
                    .clickable {
                        navController.navigate("editScreen/${task.taskId}")
                    }
            ){
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(task.taskTitle,
                        modifier = Modifier
                            .padding(horizontal = 8.dp).padding(top = 8.dp),
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )
                    Text(task.taskText,
                        modifier = Modifier.padding(horizontal = 5.dp).padding(bottom = 5.dp),
                        style = MaterialTheme.typography.body2,
                        textAlign = TextAlign.Center
                    )
                }

            }

        }
    }
}