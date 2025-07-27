package com.example.app_orcamind.ui.screens.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app_orcamind.R


@Composable
fun RegisterScreen(registerViewModel: RegisterViewModel = viewModel()) {
    val uiState by registerViewModel.uiState.collectAsState()

    val mediumPadding = dimensionResource(R.dimen.padding_medium)
    val paddingHeight = dimensionResource(R.dimen.padding_height)


    Column(
        modifier = Modifier
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding()
            .padding(
                top = paddingHeight,
                start = mediumPadding,
                end = mediumPadding
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RegisterLayout(
            responseName = uiState.userResponseRegisterName,
            responseEmail = uiState.userResponseRegisterEmail,
            responsePassword = uiState.userResponseRegisterPassword,
            responsePasswordCurrent = uiState.userResponseRegisterPasswordCurrent,
            onUserNameChanged = {
                registerViewModel.onNameChange(it)
            },
            onUserEmailChanged = {
                registerViewModel.onEmailChange(it)
            },
            onUserPasswordChanged = {
                registerViewModel.onPasswordChange(it)
            },
            onUserPasswordCurrentChanged = {
                registerViewModel.onPasswordCurrentChange(it)
            },
            onKeyboardDone = { registerViewModel.newPerformClick() },
            responseInputWrong = uiState.createUserErrorMessage != null
        )
        Spacer(modifier = Modifier.height(24.dp))
        uiState.createUserErrorMessage?.let {
            Text(
                text = it,
                fontSize = 16.sp,
                color = Color.Red
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        if (uiState.isLoading) {
            CircularProgressIndicator()
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(mediumPadding),
            verticalArrangement = Arrangement.spacedBy(mediumPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { registerViewModel.newPerformClick() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                enabled = uiState.userResponseRegisterEmail.isNotBlank() &&
                        uiState.userResponseRegisterPassword.isNotBlank() &&
                        uiState.userResponseRegisterName.isNotBlank() &&
                        uiState.userResponseRegisterPasswordCurrent.isNotBlank()
                ,
                colors = ButtonDefaults.buttonColors(
                    Color(0xFF6200EA)
                )
            ) {
                Text(
                    text = stringResource(R.string.registrar),
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun RegisterLayout(
    responseName: String,
    responseEmail: String,
    responsePassword: String,
    responsePasswordCurrent: String,
    onUserNameChanged: (String) -> Unit,
    onUserEmailChanged: (String) -> Unit,
    onUserPasswordChanged: (String) -> Unit,
    onUserPasswordCurrentChanged: (String) -> Unit,
    onKeyboardDone: () -> Unit,
    responseInputWrong: Boolean
) {

    val mediumPadding = dimensionResource(R.dimen.padding_medium)
    val maxHeight = dimensionResource(R.dimen.max_height)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(maxHeight),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(mediumPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(mediumPadding)
        ) {
            Text(
                text = stringResource(R.string.register),
                style = typography.displaySmall
            )
            HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
            OutlinedTextField(
                value = responseName,
                singleLine = true,
                shape = shapes.large,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 12.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = colorScheme.surface,
                    unfocusedContainerColor = colorScheme.surface,
                    disabledContainerColor = colorScheme.surface,
                ),
                onValueChange = onUserNameChanged,
                placeholder = { Text(text = stringResource(R.string.name)) },
                isError = responseInputWrong,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Email
                ),
                keyboardActions = KeyboardActions(
                    onDone = { onKeyboardDone() }
                )
            )
            OutlinedTextField(
                value = responseEmail,
                singleLine = true,
                shape = shapes.large,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 12.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = colorScheme.surface,
                    unfocusedContainerColor = colorScheme.surface,
                    disabledContainerColor = colorScheme.surface,
                ),
                onValueChange = onUserEmailChanged,
                placeholder = { Text(text = stringResource(R.string.email)) },
                isError = responseInputWrong,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Email
                ),
                keyboardActions = KeyboardActions(
                    onDone = { onKeyboardDone() }
                )
            )
            OutlinedTextField(
                value = responsePassword,
                singleLine = true,
                shape = shapes.large,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 12.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = colorScheme.surface,
                    unfocusedContainerColor = colorScheme.surface,
                    disabledContainerColor = colorScheme.surface
                ),
                onValueChange = onUserPasswordChanged,
                placeholder = { Text(text = stringResource(R.string.password)) },
                isError = responseInputWrong,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password
                ),
                keyboardActions = KeyboardActions(
                    onDone = { onKeyboardDone() }
                )
            )
            OutlinedTextField(
                value = responsePasswordCurrent,
                singleLine = true,
                shape = shapes.large,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 12.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = colorScheme.surface,
                    unfocusedContainerColor = colorScheme.surface,
                    disabledContainerColor = colorScheme.surface
                ),
                onValueChange = onUserPasswordCurrentChanged,
                placeholder = { Text(text = stringResource(R.string.confirmPassword)) },
                isError = responseInputWrong,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password
                ),
                keyboardActions = KeyboardActions(
                    onDone = { onKeyboardDone() }
                )
            )
            HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun RegisterPreview() {
    RegisterLayout(
        responseName = "",
        responseEmail = "",
        responsePassword = "",
        responsePasswordCurrent = "",
        onUserNameChanged = {},
        onUserEmailChanged = {},
        onUserPasswordChanged = {},
        onUserPasswordCurrentChanged = {},
        onKeyboardDone = {},
        responseInputWrong = false
    )
}