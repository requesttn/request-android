package tn.request.ui

import android.os.Bundle
import android.util.Log
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.FragmentActivity
import org.koin.android.ext.android.inject
import tn.request.R
import tn.request.preferences.SharedPreferencesDao
import tn.request.ui.theme.RequestTheme

class MainActivity : FragmentActivity() {
    private val TAG = MainActivity::class.java.name

    private val preferencesDao by inject<SharedPreferencesDao>();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        preferencesDao.getCurrentUser()?.let {
            Log.d(TAG, "Current User JWT: ${it.jwt}")
            Log.d(TAG, "Current User Firstname: ${it.firstname}")
            Log.d(TAG, "Current User Lastname: ${it.lastname}")
            Log.d(TAG, "Current User Email: ${it.email}")
        }

    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RequestTheme {
        Greeting("Android")
    }
}