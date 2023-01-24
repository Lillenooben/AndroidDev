package com.lije21dg.todo2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lije21dg.todo2.ui.theme.Todo2Theme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

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
    var taskTitle: String,
    var taskId: Int
)

var testTaskList: MutableList<TaskItem> = mutableListOf()

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

@Composable
fun ButtonBar(navController: NavHostController){
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Yellow)
    ){

        Button(onClick = {
            testTaskList.add(TaskItem("Test1", testTaskList.size))
            println("Button1 run")
            println(testTaskList)
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

@Composable
fun CreateScreen() {
    var textFieldValue by remember { mutableStateOf(TextFieldValue(""))}
    //val focusManager = LocalFocusManager.current
    Column(){
        Column(
            modifier = Modifier
        ){
            OutlinedTextField(value = textFieldValue, modifier = Modifier.background(Color.White), onValueChange = {
                textFieldValue = it
            },
                label = {Text(text = "Task Title")},
                placeholder = {Text(text = "eg. Wash dishes..")},
                trailingIcon = {Icon(
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
                )}
            )
        }
    }
}

fun removeItemAndRemakeListWithNewItemIds(task: TaskItem): MutableList<TaskItem> {
    var newList: MutableList<TaskItem> = mutableListOf()
    testTaskList.forEach {
        if (task != it) {
            var newTask = TaskItem(it.taskTitle, newList.size)
            newList.add(newTask)
            println("New Task: $newTask")
        }
    }
    println("Size: ${newList.size} List: $newList")
    return newList
}

@Composable
fun EditScreen(taskSent: TaskItem, navController: NavHostController) {
    //val task = testTaskList[taskId]
    var task = taskSent
    var textFieldValue by remember { mutableStateOf(TextFieldValue(task.taskTitle))}
    Column(){
        Column(
            modifier = Modifier
        ){
            OutlinedTextField(value = textFieldValue, modifier = Modifier.background(Color.White), onValueChange = {
                textFieldValue = it
            },
                label = {Text(text = "Task Title")},
                placeholder = {Text(text = "eg. Wash dishes..")},
                trailingIcon = {Icon(
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
                )}
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Todo2Theme {
        MainScreen()
    }
}