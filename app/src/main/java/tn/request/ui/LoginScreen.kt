package tn.request.ui

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.request.network.BackendService
import tn.request.network.model.LoginRequest
import tn.request.network.model.LoginResponse
import tn.request.ui.theme.RequestTheme

@Composable
fun LoginScreen() {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    val focusManager = LocalFocusManager.current

    val backendService = BackendService()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .clickable { focusManager.clearFocus() },
        color = MaterialTheme.colors.background,

        ) {
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

            val context = LocalContext.current
            LoginButton {
                backendService.login(
                    LoginRequest(
                        email, password
                    )
                ).enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        if (response.isSuccessful) {
                            Toast.makeText(
                                context, """
                                Login Succeed
                                ${response.body()}
                            """.trimIndent(), Toast.LENGTH_LONG
                            ).show()

                            println("Login Succeed : ${response.body()}")
                        } else {
                            Toast.makeText(
                                context, """
                                Login Failed
                                ${response.errorBody()?.string()}
                            """.trimIndent(), Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(
                            context, """
                                Login Failed with an exception
                                $t
                            """.trimIndent(), Toast.LENGTH_LONG
                        ).show()
                    }

                })
            }
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
        placeholder = { Text("**********") }
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
        LoginScreen()
    }
}