package com.example.app_orcamind.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.app_orcamind.R


@Composable
fun GoogleSignInButton(
    text: String = "Entrar com Google",
    loading: Boolean = false,
    onClick: () -> Unit
) {
    val buttonColor = Color.White
    val borderColor = Color.LightGray
    val icon = painterResource(id = R.drawable.googlelogo)

    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        border = BorderStroke(1.dp, borderColor),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = buttonColor,
            contentColor = Color.Black
        )
    ) {
        if (loading) {
            CircularProgressIndicator(
                color = Color.Gray,
                modifier = Modifier.size(20.dp),
                strokeWidth = 2.dp
            )
        } else {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = icon,
                    contentDescription = "Google logo",
                    modifier = Modifier
                        .size(24.dp)
                        .padding(end = 8.dp)
                )
                Text(
                    text = text,

                    )
            }
        }
    }
}

@Preview
@Composable
fun GoogleSignInButtonPreview() {
    GoogleSignInButton(
        text = "Entrar com Google",
        loading = false,
        onClick = {}
    )
}