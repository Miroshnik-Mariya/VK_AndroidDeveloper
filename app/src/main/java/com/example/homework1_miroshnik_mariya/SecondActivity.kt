package com.example.homework1_miroshnik_mariya

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val userText: TextView = findViewById(R.id.userText)

        // ПОЛУЧАЕМ ТЕКСТ ИЗ INTENT
        val receivedText = intent.getStringExtra("USER_TEXT")

        // Устанавливаем полученный текст в TextView
        if(receivedText.isNullOrEmpty()){
            userText.text = "Текст не получен"
        }
        else {
            userText.text = receivedText
        }
    }
}