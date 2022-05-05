package tn.request.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(viewModel: HomeViewModel, onLogout: () -> Unit) {
    val currentUser = viewModel.currentUser.observeAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Hello, ${currentUser.value?.firstname ?: "Unknown"}",
                style = MaterialTheme.typography.h5
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "I'm glad you decided to try out Request. Here are a few tips to get you up and running fast",
                style = MaterialTheme.typography.subtitle1
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = onLogout,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.onBackground
                )
                ) {
                Text("Logout", color = Color.Red)
            }
        }
    }
}