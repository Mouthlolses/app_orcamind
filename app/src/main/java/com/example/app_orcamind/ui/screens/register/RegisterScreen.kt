package com.example.app_orcamind.ui.screens.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.app_orcamind.R


@Composable
fun RegisterScreen() {


}


@Composable
fun RegisterLayout(
    ResponseName: String,
    ResponseEmail: String,
    ResponsePassword: String,
    onUserEmailChanged: (String) -> Unit,
    onUserPasswordChanged: (String) -> Unit,
    onKeyboardDone: () -> Unit,
    ResponseInputWrong: Boolean
) {

    val mediumPadding = dimensionResource(R.dimen.padding_hight)

    Card(
        modifier = Modifier
            .fillMaxWidth(),
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
            OutlinedTextField(
                value = ResponseName,
                singleLine = true,
                shape = shapes.large,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = colorScheme.surface,
                    unfocusedContainerColor = colorScheme.surface,
                    disabledContainerColor = colorScheme.surface,
                ),
                onValueChange = onUserEmailChanged,
                placeholder = { Text(text = stringResource(R.string.name)) },
                isError = ResponseInputWrong,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Email
                ),
                keyboardActions = KeyboardActions(
                    onDone = { onKeyboardDone() }
                )
            )
            OutlinedTextField(
                value = ResponseEmail,
                singleLine = true,
                shape = shapes.large,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = colorScheme.surface,
                    unfocusedContainerColor = colorScheme.surface,
                    disabledContainerColor = colorScheme.surface,
                ),
                onValueChange = onUserEmailChanged,
                placeholder = { Text(text = stringResource(R.string.email)) },
                isError = ResponseInputWrong,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Email
                ),
                keyboardActions = KeyboardActions(
                    onDone = { onKeyboardDone() }
                )
            )
            OutlinedTextField(
                value = ResponsePassword,
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
                isError = ResponseInputWrong,
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


@Preview
@Composable
fun RegisterPreview() {
    val input = ""

    RegisterLayout(
        ResponseName = input,
        ResponseEmail = input,
        ResponsePassword = input,
        ResponseInputWrong = false,
        onUserEmailChanged = {},
        onUserPasswordChanged = {},
        onKeyboardDone = {}
    )
}