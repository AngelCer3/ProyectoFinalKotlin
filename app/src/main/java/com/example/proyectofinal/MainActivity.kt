package com.example.proyectofinal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var loguear : Button
    private lateinit var registro : Button
    private lateinit var correo : EditText
    private lateinit var contrasena: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->

            loguear = findViewById(R.id.button_login)
            registro = findViewById(R.id.button_registro)
            correo = findViewById(R.id.Correo)
            contrasena = findViewById(R.id.Contrasena)

            loguear.setOnClickListener{
                val email = correo.text.toString()
                val password = contrasena.text.toString()

                if(email.isNotEmpty() && password.isNotEmpty()){
                    loginUsuario(email,password)
                }else{
                    Toast.makeText(this, "Ingresa los campos", Toast.LENGTH_SHORT).show()
                }
            }

            registro.setOnClickListener{
                val intent = Intent(this, Registro_Activity::class.java)
                startActivity(intent)
            }
            insets
        }
    }
    private fun loginUsuario(email: String, password: String){
        val loginRequest = LoginRequest(correo = email, contrasena = password)
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val response: Response<LoginResponse> = RetrofitClient.webService.login(loginRequest)
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if(loginResponse?.usuarios != null){
                        runOnUiThread {
                            Toast.makeText(this@MainActivity,"Login Exitoso", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@MainActivity, Menu_Activity::class.java)
                            startActivity(intent)
                        }
                    }else{
                        runOnUiThread {
                            Toast.makeText(this@MainActivity, "Credenciales Incorrectas", Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    runOnUiThread {
                        Toast.makeText(this@MainActivity, "Error de Conexion", Toast.LENGTH_SHORT).show()
                    }
                }
            }catch(e: Exception){
                runOnUiThread {
                    Toast.makeText(this@MainActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}