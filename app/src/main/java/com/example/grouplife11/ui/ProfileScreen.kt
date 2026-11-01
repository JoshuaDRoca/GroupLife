package com.example.grouplife11.ui

import android.net.Uri
import android.print.PrintAttributes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.example.grouplife11.R.drawable
import com.example.grouplife11.Screens
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen (
    navController : NavHostController
) {
    // taken from Firebase docs
    val user = Firebase.auth.currentUser
    var name : String?
    var email : String?

    // initializing name and email based on existence of user
    if (user != null) {
        name = user.displayName
        email = user.email
    } else {
        name = ""
        email = ""
    }
    // the email is somehow pulling wrong...

    val profileImage = ImageRequest.Builder(LocalContext.current)
        .data(user?.photoUrl).build()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile")},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Blue,
                    titleContentColor = Color.White
                )
            )

        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background((Color.White))
        ) {
            Row(
                Modifier.fillMaxWidth().padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                //This profile image is not showing up
                AsyncImage(
                    model = profileImage,
                    contentDescription = "Profile Picture"
                )

            }
            Spacer(Modifier.height(8.dp))
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = "Name: $name")
                    Spacer(Modifier.height(4.dp))
                    Text(text = "Email Address: $email")
                }
            }



            TextButton(onClick = {
                navController.navigate(Screens.HomeScreen.name) {
                    popUpTo(Screens.ProfileScreen.name) { inclusive = true }
                }
            }) {
                Text("Back to Home Screen.")
            }
        }
    }
}

