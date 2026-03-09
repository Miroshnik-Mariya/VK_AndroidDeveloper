package com.example.homework1_miroshnik_mariya

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val textEnter: EditText = findViewById(R.id.editTextEnterText)
        val openActivity2: Button = findViewById(R.id.btnAct2)
        val callFriend: Button = findViewById(R.id.btnCall)
        val shareText: Button = findViewById(R.id.btnShare)
        val intent = Intent(this, SecondActivity::class.java)


        //ПЕРВОЕ ЗАДАНИЕ
        openActivity2.setOnClickListener {
            val enteredText = textEnter.text.toString()
            intent.putExtra("USER_TEXT", enteredText)
            startActivity(intent)
        }


        //ВТОРОЕ ЗАДАНИЕ
        callFriend.setOnClickListener {
            val phoneNumber = textEnter.text.toString().trim()

            if (TextUtils.isEmpty(phoneNumber)) {
                Toast.makeText(this, "Введите номер телефона", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val cleanNumber = phoneNumber.replace("[^0-9+]".toRegex(), "")

            if (cleanNumber.length < 11 || cleanNumber.length > 11) {
                Toast.makeText(this,"Введите корректный номер телефона (минимум 11 цифр)",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            try {
                val phoneUri = Uri.parse("tel:$cleanNumber")
                val dialIntent = Intent(Intent.ACTION_DIAL, phoneUri)

                if (dialIntent.resolveActivity(packageManager) != null) {
                    startActivity(dialIntent)
                } else {
                    Toast.makeText(
                        this,"На устройстве нет приложения для совершения звонков",Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this,
                    "Ошибка при попытке вызова: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        //ТРЕТЬЕ ЗАДАНИЕ
        shareText.setOnClickListener {

            val str = textEnter.text.toString().trim()

            if (TextUtils.isEmpty(str)) {
                Toast.makeText(this, "Введите текст для отправки: ", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, str)
                type = "text/plain"
            }
                startActivity(Intent.createChooser(shareIntent, "Поделиться через: "))


            }
        }

}