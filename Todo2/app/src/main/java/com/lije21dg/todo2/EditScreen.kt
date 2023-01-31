package com.lije21dg.todo2

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavHostController

@Composable
fun EditScreen(taskSent: TaskItem, navController: NavHostController) {
    //val task = testTaskList[taskId]
    var task = taskSent
    var taskTitleFieldValue by remember { mutableStateOf(TextFieldValue(task.taskTitle)) }
    var taskTextFieldValue by remember { mutableStateOf(TextFieldValue(task.taskText)) }
    var errorList = remember { mutableStateListOf<String>() }
    Column(){
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){

            Text(text = "Edit task",
                style = MaterialTheme.typography.h3 )
            errorList.forEach{
                Text(it)
            }

            OutlinedTextField(value = taskTitleFieldValue, modifier = Modifier
                .background(Color.White)
                .fillMaxWidth(), onValueChange = {
                taskTitleFieldValue = it
            },
                label = { Text(text = "Task Title") },
                placeholder = { Text(text = "eg. Wash dishes..") },
                maxLines = 5,
                trailingIcon = {
                    Icon(
                    Icons.Filled.Done,
                    contentDescription = "Add task",
                    modifier = Modifier
                        .clickable {
                            errorList.clear()
                            if(taskTitleFieldValue.text.length in taskTitleMinLength..taskTitleMaxLength && taskTextFieldValue.text.length <= 120){
                                task.taskTitle = taskTitleFieldValue.text
                                navController.navigate("taskScreen"){
                                    popUpTo("taskScreen"){
                                        inclusive = true
                                    }
                                }
                            }
                            else{
                                if(taskTitleFieldValue.text.length !in taskTitleMinLength..taskTitleMaxLength)
                                    errorList.add("Title must be between ${taskTitleMinLength} and ${taskTitleMaxLength} characters long (currently: ${taskTitleFieldValue.text.length})")
                                if(taskTextFieldValue.text.length > taskTextMaxLength)
                                    errorList.add("Description must be less than ${taskTextMaxLength} characters (currently: ${taskTextFieldValue.text.length})")
                            }
                        }
                )
                }
            )
            OutlinedTextField(value = taskTextFieldValue, modifier = Modifier
                .background(Color.White)
                .fillMaxWidth(), onValueChange = {
                taskTextFieldValue = it
            },
                label = { Text(text = "Task Description") },
                placeholder = { Text(text = "Describe your task") },
                maxLines = 5
            )
            Button(onClick = {
                testTaskList = removeItemAndRemakeListWithNewItemIds(task)
                task = TaskItem("", "", 0)
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