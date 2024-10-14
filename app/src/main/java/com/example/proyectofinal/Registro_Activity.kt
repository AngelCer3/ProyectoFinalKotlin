package com.example.proyectofinal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity


class Registro_Activity : AppCompatActivity() {

    private lateinit var registrar: Button
    private lateinit var cancelar: Button
    private lateinit var nombres: EditText
    private lateinit var apellidoPaterno: EditText
    private lateinit var apellidoMaterno: EditText
    private lateinit var edad: EditText
    private lateinit var fechaNacimiento: EditText
    private lateinit var correo: EditText
    private lateinit var contrasena: EditText
    private lateinit var radioGenero: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        registrar = findViewById(R.id.button_Registrar)
        cancelar = findViewById(R.id.button_cancelar)
        nombres = findViewById(R.id.Nombres)
        apellidoPaterno = findViewById(R.id.apPaterno)
        apellidoMaterno = findViewById(R.id.apMaterno)
        edad = findViewById(R.id.Edad)
        fechaNacimiento = findViewById(R.id.FechaNacimiento)
        correo = findViewById(R.id.Correo)
        contrasena = findViewById(R.id.Contrasena)
        radioGenero = findViewById(R.id.radioGenero)

        registrar.setOnClickListener {

            val nombre = nombres.text.toString()
            val apellidoP = apellidoPaterno.text.toString()
            val apellidoM = apellidoMaterno.text.toString()
            val edadUsuario = edad.text.toString()
            val fechaNac = fechaNacimiento.text.toString()
            val correoUsuario = correo.text.toString()
            val contrasenaUsuario = contrasena.text.toString()
            val generoSeleccionadoId = radioGenero.checkedRadioButtonId

            val generoSeleccionado = when (generoSeleccionadoId) {
                R.id.radioMasculino -> "Masculino"
                R.id.radioFemenino -> "Femenino"
                R.id.radioOtro -> "Otro"
                else -> "No especificado"
            }


        }

        cancelar.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
