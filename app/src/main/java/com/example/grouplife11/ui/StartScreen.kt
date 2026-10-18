package com.example.grouplife11.ui

import android.content.Context
import android.credentials.CredentialOption
import android.graphics.Paint
import androidx.credentials.GetCredentialRequest
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.credentials.Credential
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.credentials.CredentialManager
import androidx.navigation.compose.composable
import com.example.grouplife11.R
import com.example.grouplife11.R.*
import com.example.grouplife11.Screens
import com.example.grouplife11.ui.theme.GroupLife11Theme
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.intellij.lang.annotations.JdkConstants
import java.security.SecureRandom
import kotlin.io.encoding.Base64


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartScreen (
    navController: NavHostController
){
    // email and password states for regular auth
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Context and scope for google sign-in
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize()
                .background(Color.White).padding(padding)
                .padding(horizontal = 14.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome to GroupLife!",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 16.dp),
                textAlign = TextAlign.Center
            )
            // added for email/password login
            Spacer(Modifier.height(24.dp))
            OutlinedTextField(
                value = email,
                onValueChange = {email = it},
                label = {Text("Email")},
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(
                value = password,
                onValueChange = {password = it},
                label = {Text("Password")},
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(24.dp))
            // contact firebase to sign in to the account
            Button(onClick = {
                Firebase.auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
                            navController.navigate(Screens.HomeScreen.name) {
                                popUpTo(Screens.StartScreen.name) { inclusive = true }
                            }
                        } else {
                            Toast.makeText(context, "Error.", Toast.LENGTH_SHORT).show()
                        }
                    }
            }) {
                Text("Log in")
            }
            Spacer(Modifier.height(8.dp))
            TextButton(
                onClick = { coroutineScope.launch { launchButton(context, navController) } },
                Modifier.width(300.dp).height(75.dp)
            ) {
                Image(
                    painter = painterResource(id = drawable.siwg_button),
                    contentDescription = "sign-in button",
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(Modifier.height(8.dp))
            TextButton(onClick = {
                navController.navigate(Screens.SignUp.name) {
                    popUpTo(Screens.StartScreen.name) { inclusive = true }
                }
            }) {
                Text("No account? Sign up here")
            }
        }
    }
}

private suspend fun launchButton (
    context: Context,
    navController: NavHostController
) {
    try {
        // Instantiating a Google sign-in request with firebase client id
        val signInWithGoogleOption = GetSignInWithGoogleOption
            .Builder(serverClientId = context.getString(R.string.web_client_id))
            .build()

        // Instantiate a credential request using the Google sign-in option
        val request = GetCredentialRequest.Builder()
            .addCredentialOption(signInWithGoogleOption)
            .build()

        // Retrieve user's available credentials with getCredential
        val result = CredentialManager.create(context).getCredential(
            request = request,
            context = context
        )

    } catch (e: Exception) {
    }

    //Navigate to Profile Screen
    navController.navigate(Screens.HomeScreen.name)
}


//currently not in use
suspend fun launchBottomSheet(
    context: Context,
    onRequestResult: (Credential) -> Unit
)   {

    try {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setServerClientId(context.getString(R.string.web_client_id))
            .setNonce("")
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        val result = CredentialManager.create(context).getCredential(
            request = request,
            context = context
        )

    } catch (e: Exception) {
    }
}
