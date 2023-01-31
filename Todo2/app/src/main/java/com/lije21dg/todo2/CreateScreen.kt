package com.lije21dg.todo2

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun CreateScreen() {
    var taskTitleFieldValue by remember { mutableStateOf(TextFieldValue("")) }
    var taskTextFieldValue by remember { mutableStateOf(TextFieldValue("")) }
    var messageList = remember { mutableStateListOf<String>() }
    Column(){
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(text = "Create a task",
            style = MaterialTheme.typography.h3 )
            messageList.forEach{
                Text(it)
            }

            OutlinedTextField(value = taskTitleFieldValue, modifier = Modifier.background(Color.White).fillMaxWidth(), onValueChange = {
                taskTitleFieldValue = it
            },
                label = { Text(text = "Task Title") },
                placeholder = { Text(text = "eg. Wash dishes..") },
                maxLines = 5,
                trailingIcon = {
                    Icon(
                    Icons.Filled.Add,
                    contentDescription = "Add task",
                    modifier = Modifier
                        .clickable {
                            messageList.clear()
                            if(taskTitleFieldValue.text.length in taskTitleMinLength..taskTitleMaxLength && taskTextFieldValue.text.length <= 120){
                                testTaskList.add(TaskItem(taskTitle = taskTitleFieldValue.text, taskTextFieldValue.text, testTaskList.size))
                                taskTitleFieldValue = TextFieldValue("")
                                taskTextFieldValue = TextFieldValue("")
                                messageList.add("Task added!")
                            }
                            else{
                                if(taskTitleFieldValue.text.length !in taskTitleMinLength..taskTitleMaxLength)
                                    messageList.add("Title must be between ${taskTitleMinLength} and ${taskTitleMaxLength} characters long (currently: ${taskTitleFieldValue.text.length})")
                                if(taskTextFieldValue.text.length > taskTextMaxLength)
                                    messageList.add("Description must be less than ${taskTextMaxLength} characters (currently: ${taskTextFieldValue.text.length})")
                            }
                        }
                )
                }
            )
            OutlinedTextField(value = taskTextFieldValue, modifier = Modifier.background(Color.White).fillMaxWidth(), onValueChange = {
                taskTextFieldValue = it
            },
                label = { Text(text = "Task Description") },
                placeholder = { Text(text = "Describe your task") },
                maxLines = 5
            )
        }
    }
}
