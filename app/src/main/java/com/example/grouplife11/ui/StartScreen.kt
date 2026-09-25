package com.example.grouplife11.ui

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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
import com.example.grouplife11.ui.theme.GroupLife11Theme
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.intellij.lang.annotations.JdkConstants
import java.security.SecureRandom
import kotlin.io.encoding.Base64


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartScreen (
    onSignInClicked: () -> Unit
){
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

            Button(
                onClick = { coroutineScope.launch { launchButton(context, onSignInClicked) } },
                modifier = Modifier
                    .padding(10.dp, 50.dp)
            ) {
                Image(
                    painter = painterResource(id = drawable.siwg_button),
                    contentDescription = "sign-in button",
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        }
    }
}

private suspend fun launchButton (
    context: Context,
    onSignInClicked: () -> Unit
) {
    try {
        val signInWithGoogleOption = GetSignInWithGoogleOption
            .Builder(serverClientId = context.getString(R.string.web_client_id))
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(signInWithGoogleOption)
            .build()

        val result = CredentialManager.create(context).getCredential(
            request = request,
            context = context
        )

    } catch (e: Exception) {
    }

    //Navigate to Profile Screen
    onSignInClicked()
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun StartScreenPreview() {
    GroupLife11Theme() {
        StartScreen {}
    }
}