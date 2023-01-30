package com.lije21dg.todo2

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun CreateScreen() {
    var textFieldValue by remember { mutableStateOf(TextFieldValue("")) }
    //val focusManager = LocalFocusManager.current
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
                    Icons.Filled.Add,
                    contentDescription = "Add task",
                    modifier = Modifier
                        .clickable {
                            if(textFieldValue.text.isNotEmpty()){
                                testTaskList.add(TaskItem(taskTitle = textFieldValue.text, testTaskList.size))
                                textFieldValue = TextFieldValue("")
                                //focusManager.clearFocus()
                            }
                        }
                )
                }
            )
        }
    }
}
