package com.example.app_orcamind.ui.screens.login

import android.content.Context
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.app_orcamind.R
import com.example.app_orcamind.ui.components.GoogleSignInButton
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity


@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = viewModel(),
    navController: NavHostController,
    context: Context = LocalContext.current
) {

    val mediumPadding = dimensionResource(R.dimen.padding_medium)

    val oneTapClient = remember { Identity.getSignInClient(context) }

    val signInRequest = remember {
        BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId("279046583617-3caa6ek08srq266ts2njvaq5duj9ihhk.apps.googleusercontent.com")
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->

        try {
            val credential = oneTapClient.getSignInCredentialFromIntent(result.data)
            val idToken = credential.googleIdToken

            if (idToken != null) {
                loginViewModel.signInWithGoogle(
                    idToken,
                    onSuccess = { Log.i("Login", "Sucesso") },
                    onFailure = { error -> Log.i("Login", "Erro: $error") }
                )
            }

        } catch (e: Exception) {
            Log.e("Login", "Erro ao obter token", e)
        }
    }


    // Coleta dos fluxos da ViewModel como estados observáveis
    val isLoading by loginViewModel.isLoading.collectAsState()
    val loginErrorMessage by loginViewModel.loginErrorMessage.collectAsState()
    val loginSuccess by loginViewModel.loginSuccess.collectAsState()
    val email by loginViewModel.userResponseEmail.collectAsState()
    val password by loginViewModel.userResponsePassword.collectAsState()

    // Se login foi bem-sucedido, navegar
//    LaunchedEffect(loginSuccess) {
//        if (loginSuccess) {
//            navController.navigate("homeScreen") {
//                popUpTo("loginScreen") { inclusive = true }
//            }
//        }
//    }

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding()
            .padding(mediumPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LoginLayout(
            userResponseEmail = email,
            userResponsePassword = password,
            isGuessWrong = loginErrorMessage != null,
            onKeyboardDone = { loginViewModel.performLogin() },
            modifier = Modifier
                .padding(mediumPadding),
            onUserEmailChanged = { newEmail -> loginViewModel.updateUserEmail(newEmail) },
            onUserPasswordChanged = { newPassword -> loginViewModel.updateUserPassword(newPassword) }
        )
        loginErrorMessage?.let { error ->
            Text(
                text = error,
                color = colorScheme.error,
                modifier = Modifier.padding(top = mediumPadding)
            )
        }
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(top = mediumPadding))
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(mediumPadding),
            verticalArrangement = Arrangement.spacedBy(mediumPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    colorResource(R.color.blue_primary)
                ),
                onClick = { loginViewModel.performLogin() }
            ) {
                Text(
                    text = stringResource(R.string.submit),
                    fontSize = 16.sp
                )
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    colorResource(R.color.blue_primary)
                ),
                onClick = { navController.navigate("registerScreen") }
            ) {
                Text(
                    text = stringResource(R.string.criarConta),
                    fontSize = 16.sp
                )
            }
            Text(
                text = "Ou"
            )
            GoogleSignInButton(
                text = "Entrar com Google",
                loading = false,
                onClick = {
                    oneTapClient.beginSignIn(signInRequest)
                        .addOnSuccessListener { result ->
                            val intentSenderRequest =
                                IntentSenderRequest.Builder(result.pendingIntent.intentSender)
                                    .build()
                            launcher.launch(intentSenderRequest)
                            Log.i("Login", "Sucesso")
                        }
                        .addOnFailureListener { e ->
                            Log.e("Login", "Erro ao iniciar signIn: ${e.message}")
                        }
                }
            )
        }
    }

}


@Composable
fun LoginLayout(
    userResponseEmail: String,
    userResponsePassword: String,
    isGuessWrong: Boolean,
    onUserEmailChanged: (String) -> Unit,
    onUserPasswordChanged: (String) -> Unit,
    onKeyboardDone: () -> Unit,
    modifier: Modifier = Modifier
) {
    val mediumPadding = dimensionResource(R.dimen.padding_medium)

    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(mediumPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(mediumPadding)
        ) {
            HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
            Text(
                text = "Bem Vindo",
                style = typography.displayMedium
            )
            HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
            Text(
                text = stringResource(R.string.access),
                modifier = Modifier
                    .padding(top = 12.dp),
                textAlign = TextAlign.Center,
                style = typography.titleMedium,
            )
            OutlinedTextField(
                value = userResponseEmail,
                singleLine = true,
                shape = shapes.large,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 6.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = colorScheme.surface,
                    unfocusedContainerColor = colorScheme.surface,
                    disabledContainerColor = colorScheme.surface,
                ),
                onValueChange = onUserEmailChanged,
                placeholder = { Text(text = stringResource(R.string.email)) },
                isError = isGuessWrong,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Email
                ),
                keyboardActions = KeyboardActions(
                    onDone = { onKeyboardDone() }
                )
            )
            OutlinedTextField(
                value = userResponsePassword,
                singleLine = true,
                shape = shapes.large,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = colorScheme.surface,
                    unfocusedContainerColor = colorScheme.surface,
                    disabledContainerColor = colorScheme.surface
                ),
                onValueChange = onUserPasswordChanged,
                placeholder = { Text(text = stringResource(R.string.password)) },
                isError = isGuessWrong,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password
                ),
                keyboardActions = KeyboardActions(
                    onDone = { onKeyboardDone() }
                )
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginLayout(
        userResponseEmail = "",
        userResponsePassword = "",
        isGuessWrong = false,
        onUserEmailChanged = {},
        onUserPasswordChanged = {},
        onKeyboardDone = {},
        modifier = Modifier
    )
}