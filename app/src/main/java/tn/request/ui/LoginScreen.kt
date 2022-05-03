package tn.request.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tn.request.ui.theme.RequestTheme

@Composable
fun LoginScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        EmailTextField()

        Spacer(modifier = Modifier.height(12.dp))

        PasswordTextField()

        Spacer(modifier = Modifier.height(24.dp))

        LoginButton {
            println("Login button clicked")
        }
    }
}

@Composable
fun EmailTextField() {
    var emailValue by remember { mutableStateOf(TextFieldValue("")) }

    TextField(
        value = emailValue,
        onValueChange = { emailValue = it },
        modifier = Modifier.fillMaxWidth(),
        label = { Text("Email") },
        singleLine = true
    )
}

@Composable
fun PasswordTextField() {
    var passwordValue by remember { mutableStateOf(TextFieldValue("")) }

    TextField(
        value = passwordValue,
        onValueChange = { passwordValue = it },
        modifier = Modifier.fillMaxWidth(),
        label = { Text("Password") },
        singleLine = true
    )
}

@Composable
fun LoginButton(onLogin: () -> Unit) {
    Box(contentAlignment = Alignment.CenterEnd, modifier = Modifier.fillMaxWidth()) {
        Button(
            onClick = onLogin,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(12.dp)

        ) {
            Text("Login", style = MaterialTheme.typography.button)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultView() {
    RequestTheme {
        LoginScreen()
    }
}