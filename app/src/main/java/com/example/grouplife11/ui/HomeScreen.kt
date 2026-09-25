package com.example.grouplife11.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.TestModifierUpdaterLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.grouplife11.Event
import com.example.grouplife11.FirebaseDB
import com.example.grouplife11.R
import com.example.grouplife11.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen (
    navController: NavHostController
) {
    val events = remember { mutableStateListOf<Event>() }

    LaunchedEffect(Unit) {
        FirebaseDB.getEvents {
            events.clear()
            events.addAll(it)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Events")},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Blue,
                    titleContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Screens.AddScreen.name)
            }) {
                Icon(painterResource(id = R.drawable.plus_icon), contentDescription = "Add" )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background((Color.White))
        ) {
            LazyColumn() {
                items(events) { event ->
                    Card(
                        modifier = Modifier
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                            .fillMaxWidth()
                            .clickable {},
                        elevation = CardDefaults.cardElevation(4.dp),
                        colors = CardDefaults.cardColors(Color.White),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Box (
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(Modifier.padding(16.dp)) {
                                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(text = "Host: ${event.host}", fontWeight = FontWeight.SemiBold)
                                    Spacer(Modifier.width(24.dp))
                                }
                                Spacer(Modifier.width(8.dp))

                                Box(Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp, vertical = 8.dp)
                                    .border(
                                        1.dp,
                                        Color.Blue,
                                        RoundedCornerShape(8.dp)
                                    )
                                    .padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ){
                                    Text(
                                        text = event.title,
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                                Spacer(Modifier.width(8.dp))
                                Row(Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = "Place: ${event.location}")
                                    Text(text = "Time: ${event.startTime}")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}