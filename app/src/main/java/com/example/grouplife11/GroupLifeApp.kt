package com.example.grouplife11

import android.content.Context
import android.credentials.CredentialOption
import android.graphics.Paint
import androidx.credentials.GetCredentialRequest
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.credentials.Credential
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.credentials.CredentialManager
import androidx.navigation.compose.composable
import com.example.grouplife11.R.*
import com.example.grouplife11.ui.HomeScreen
import com.example.grouplife11.ui.ProfileScreen
import com.example.grouplife11.ui.StartScreen
import com.example.grouplife11.ui.AddScreen
import com.example.grouplife11.ui.theme.GroupLife11Theme
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.security.SecureRandom
import kotlin.io.encoding.Base64


enum class Screens() {
    StartScreen,
    ProfileScreen,
    HomeScreen,
    AddScreen
}

@Composable
fun GroupLifeApp() {
    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.StartScreen.name
    ) {
        composable(route = Screens.StartScreen.name) {
            StartScreen(onSignInClicked = {navController.navigate(Screens.HomeScreen.name)})
        }
        composable(route = Screens.ProfileScreen.name) {
            ProfileScreen()
        }
        composable (route = Screens.HomeScreen.name) {
            HomeScreen(navController)
        }
        composable (route = Screens.AddScreen.name) {
            AddScreen(navController)
        }
    }
}