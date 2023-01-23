package com.lije21dg.todo2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lije21dg.todo2.ui.theme.Todo2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Todo2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

data class TaskItem(
    val taskTitle: String,
    val taskText: String,
    val taskStatusBool: Boolean
)

val testTaskList: MutableList<TaskItem> = mutableListOf()

@Composable
fun MainScreen() {

    val navController = rememberNavController()
    Column(
        modifier = Modifier
            .background(Color.Red)
            .padding(8.dp)
    ){
        NavHost(
            navController,
            "taskScreen"){
            composable("taskScreen"){
                TaskScreen(testTaskList)
            }
            composable("createScreen")
            {
                CreateScreen()
            }
        }
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Yellow)
        ){

            Button(onClick = {
                testTaskList.add(TaskItem("Test1", "", false))
                println("Button1 run")
                println(testTaskList)
            }) {
                Text("Option 1")
            }

            Button(onClick = { navController.navigate("taskScreen") {
                popUpTo("taskScreen"){
                    inclusive = true
                }
            } }) {
                Text("Option 2")
            }

            Button(onClick = { navController.navigate("createScreen"){
                launchSingleTop = true
                popUpTo("taskScreen")
            } }) {
                Text("Option 3")
            }
        }
    }
}

@Composable
fun TaskScreen(tasks: MutableList<TaskItem>) {
    LazyColumn(
        modifier = Modifier.background(Color.White).fillMaxWidth().padding(8.dp)
    ){
        if(tasks.isEmpty()){
            item{
                Text("List is Empty",
                    modifier = Modifier
                )
            }
        }
        items(tasks){task ->
            Text(task.taskTitle,
                modifier = Modifier
            )
        }
    }
}

@Composable
fun CreateScreen() {
    var textFieldValue by remember { mutableStateOf(TextFieldValue(""))}
    Column(){
        OutlinedTextField(value = textFieldValue, onValueChange = {
            textFieldValue = it
        },
            label = {Text(text = "Task Title")},
            placeholder = {Text(text = "eg. Wash dishes..")},
            trailingIcon = {Icon(
                Icons.Filled.Add,
                contentDescription = "Add task",
                modifier = Modifier.clickable {
                    //textFieldValue.text = ""
                    testTaskList.add(TaskItem(taskTitle = textFieldValue.text, "", false))
                }
            )}
        )
        /*Button(onClick = {
            testTaskList.add(TaskItem(taskTitle = textFieldValue.text, "", false))

        }) {
            Text("Add")
        }*/
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Todo2Theme {
        MainScreen()
    }
}