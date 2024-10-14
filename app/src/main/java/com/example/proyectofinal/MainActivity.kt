package com.example.proyectofinal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var loguear : Button
    private lateinit var registro : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->

            loguear = findViewById(R.id.button_login)
            registro = findViewById(R.id.button_registro)

            loguear.setOnClickListener{
                val intent = Intent(this,Menu_Activity::class.java)
                startActivity(intent)
            }

            registro.setOnClickListener{
                val intent = Intent(this, Registro_Activity::class.java)
                startActivity(intent)
            }
            insets
        }
    }
}