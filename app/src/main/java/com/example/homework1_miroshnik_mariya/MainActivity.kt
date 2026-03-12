package com.example.homework1_miroshnik_mariya

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            greeting("Привет, Compose!")
        }
    }
}

@Composable
fun greeting(name: String) {
    Text(text = name)
}

@Preview(showBackground = true)
@Composable
fun greetingPreview() {
    greeting("Привет, Compose!")
}