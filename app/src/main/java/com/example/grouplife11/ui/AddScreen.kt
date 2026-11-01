package com.example.grouplife11.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.grouplife11.Event
import com.example.grouplife11.FirebaseDB
import com.example.grouplife11.R.drawable
import com.example.grouplife11.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen (
    navController: NavHostController
) {
    var title by remember { mutableStateOf("")}
    var host by remember { mutableStateOf("")}
    var location by remember { mutableStateOf("")}
    var startTime by remember { mutableStateOf("")}
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Event")},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Blue,
                    titleContentColor = Color.White
                ),
                navigationIcon = {TextButton(
                    onClick = {navController.navigate(Screens.ProfileScreen.name)},
                    Modifier.width(60.dp).height(50.dp)
                ) {
                    Image(
                        painter = painterResource(id = drawable.account),
                        contentDescription = "profile button",
                        contentScale = ContentScale.FillBounds
                    )
                }}
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize()
                .background(Color.White).padding(padding)
                .padding(horizontal = 14.dp, vertical = 12.dp)
                .verticalScroll(rememberScrollState())
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = {title = it},
                label = {Text("Enter your event title")},
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = host,
                onValueChange = {host = it},
                label = {Text("Enter the event host")},
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = location,
                onValueChange = {location = it},
                label = {Text("Enter your event location")},
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = startTime,
                onValueChange = {startTime = it},
                label = {Text("Enter your event start time")},
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(Modifier.height(16.dp))

            Button(onClick = {
                FirebaseDB.addEvent(Event("", title, host, location, startTime))
                navController.popBackStack()
                Toast.makeText(context, "Event Saved", Toast.LENGTH_SHORT).show()
            }, modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save")
            }

            Spacer(Modifier.height(16.dp))

            TextButton(onClick = {
                navController.navigate(Screens.HomeScreen.name) {
                    popUpTo(Screens.AddScreen.name) { inclusive = true }
                }
            }) {
                Text("Back to Home Screen.")
            }

        }
    }
}