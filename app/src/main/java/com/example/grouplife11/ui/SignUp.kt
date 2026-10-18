package com.example.grouplife11.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.grouplife11.Screens
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun SignUp (
    navController: NavHostController
) {
    // email and password states for regular auth
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize().padding(16.dp),
        contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Sign Up", style = MaterialTheme.typography.headlineMedium)
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
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(24.dp))

            // Saving local context for this specific toast.
            val toastContext = LocalContext.current

            // contact firebase to create the account
            Button(onClick = {
                Firebase.auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(toastContext, "Signup successful", Toast.LENGTH_SHORT).show()
                            navController.navigate(Screens.StartScreen.name) {
                                popUpTo(Screens.SignUp.name) { inclusive = true }
                            }
                        } else {
                            Toast.makeText(toastContext, "Error.", Toast.LENGTH_SHORT).show()
                        }
                    }
            }) {
                Text("Sign Up")
            }
            Spacer(Modifier.height(8.dp))
            TextButton(onClick = {
                navController.navigate(Screens.StartScreen.name) {
                    popUpTo(Screens.SignUp.name) { inclusive = true }
                }
            }) {
                Text("Already signed up? Login here")
            }
        }
    }

}
