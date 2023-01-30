package com.lije21dg.todo2

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavHostController

@Composable
fun EditScreen(taskSent: TaskItem, navController: NavHostController) {
    //val task = testTaskList[taskId]
    var task = taskSent
    var textFieldValue by remember { mutableStateOf(TextFieldValue(task.taskTitle)) }
    Column(){
        Column(
            modifier = Modifier
        ){
            OutlinedTextField(value = textFieldValue, modifier = Modifier.background(Color.White), onValueChange = {
                textFieldValue = it
            },
                label = { Text(text = "Task Title") },
                placeholder = { Text(text = "eg. Wash dishes..") },
                trailingIcon = {
                    Icon(
                    Icons.Filled.Done,
                    contentDescription = "Add task",
                    modifier = Modifier
                        .clickable {
                            if(textFieldValue.text.isNotEmpty()){
                                task.taskTitle = textFieldValue.text
                                navController.navigate("taskScreen"){
                                    popUpTo("taskScreen"){
                                        inclusive = true
                                    }
                                }
                            }
                        }
                )
                }
            )
            Button(onClick = {
                testTaskList = removeItemAndRemakeListWithNewItemIds(task)
                task = TaskItem("", 0)
                navController.navigate("taskScreen"){
                    popUpTo("taskScreen"){
                        inclusive = true
                    }
                }
            }) {
                Text(text = "Remove Task")
            }
        }
    }
}