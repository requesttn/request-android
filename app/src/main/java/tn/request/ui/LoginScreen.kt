package tn.request.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.get
import tn.request.model.Status
import tn.request.ui.theme.RequestTheme


@Composable
fun LoginScreen(viewModel: LoginViewModel, onLoginSucceed: () -> Unit) {

    val userLoginState by viewModel.userLoginEvent.observeAsState()

    val focusManager = LocalFocusManager.current

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .clickable { focusManager.clearFocus() },
        color = MaterialTheme.colors.background,

        ) {
        when (userLoginState?.status ?: Status.ERROR) {
            Status.IDLE, Status.ERROR -> {
                LoginViewContent(viewModel)

                if (userLoginState!!.status == Status.ERROR) {
                    Toast.makeText(
                        get(),
                        userLoginState!!.message ?: "Unknown error",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            Status.LOADING -> {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    LoginViewContent(viewModel)
                    Box(
                        modifier = Modifier.fillMaxSize()
                            .background(color = Color.Black.copy(alpha = 0.25f))
                            .clickable {  },
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(50.dp, 50.dp)
                        )
                    }
                }
            }
            Status.SUCCESS -> {
                onLoginSucceed()
                Toast.makeText(get(), "Navigating to home screen...", Toast.LENGTH_LONG).show()
            }
        }
    }

}

@Composable
fun LoginViewContent(viewModel: LoginViewModel) {

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        EmailTextField(email) {
            email = it
        }

        Spacer(modifier = Modifier.height(12.dp))

        PasswordTextField(password) {
            password = it
        }

        Spacer(modifier = Modifier.height(24.dp))

        LoginButton {
            viewModel.login(email, password)
        }
    }
}

@Composable
fun EmailTextField(email: String, onEmailChange: (String) -> Unit) {
    TextField(
        value = email,
        onValueChange = onEmailChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text("Email") },
        singleLine = true,
        placeholder = { Text("user@email.com") }
    )
}

@Composable
fun PasswordTextField(password: String, onPasswordChange: (String) -> Unit) {
    TextField(
        value = password,
        onValueChange = onPasswordChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text("Password") },
        singleLine = true,
        placeholder = { Text("****************") }
    )
}

@Composable
fun LoginButton(onLogin: () -> Unit) {
    Box(contentAlignment = Alignment.CenterEnd, modifier = Modifier.fillMaxWidth()) {
        Button(
            onClick = onLogin,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
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
        LoginScreen(get(), {})
    }
}