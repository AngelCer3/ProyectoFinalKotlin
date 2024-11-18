package com.example.proyectofinal

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class Acutalizar_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acutalizar)

        val idUsuario = intent.getIntExtra("id_usuario", -1)
        val nombre = intent.getStringExtra("nombre")
        val apPaterno = intent.getStringExtra("apPaterno")
        val apMaterno = intent.getStringExtra("apMaterno")
        val edad = intent.getStringExtra("edad")
        val genero = intent.getStringExtra("genero")
        val correo = intent.getStringExtra("correo")
        val contrasena = intent.getStringExtra("contrasena")
        val fechaNacimiento = intent.getStringExtra("fechaNacimiento")

        val editTextNombre = findViewById<EditText>(R.id.Nombres)
        val editTextApPaterno = findViewById<EditText>(R.id.apPaterno)
        val editTextApMaterno = findViewById<EditText>(R.id.apMaterno)
        val editTextEdad = findViewById<EditText>(R.id.Edad)
        val editTextCorreo = findViewById<EditText>(R.id.Correo)
        val editTextContrasena = findViewById<EditText>(R.id.Contrasena)

        editTextNombre.setText(nombre)
        editTextApPaterno.setText(apPaterno)
        editTextApMaterno.setText(apMaterno)
        editTextEdad.setText(edad)
        editTextCorreo.setText(correo)
        editTextContrasena.setText(contrasena)

        val radioGroupGenero = findViewById<RadioGroup>(R.id.radioGenero)
        when (genero) {
            "Masculino" -> findViewById<RadioButton>(R.id.radioMasculino).isChecked = true
            "Femenino" -> findViewById<RadioButton>(R.id.radioFemenino).isChecked = true
            "Otro" -> findViewById<RadioButton>(R.id.radioOtro).isChecked = true
        }

        val btnFechaNacimiento = findViewById<Button>(R.id.FechaNacimiento)
        btnFechaNacimiento.text = fechaNacimiento

        btnFechaNacimiento.setOnClickListener {
            showDatePickerDialog(btnFechaNacimiento)
        }


        val btnActualizar = findViewById<Button>(R.id.btnActualizar)
        btnActualizar.setOnClickListener {

            val nombreNuevo = editTextNombre.text.toString()
            val apPaternoNuevo = editTextApPaterno.text.toString()
            val apMaternoNuevo = editTextApMaterno.text.toString()
            val edadNuevo = editTextEdad.text.toString()
            val generoNuevo = obtenerGeneroSeleccionado()
            val correoNuevo = editTextCorreo.text.toString()
            val contrasenaNueva = editTextContrasena.text.toString()
            val fechaNacimientoNuevo = btnFechaNacimiento.text.toString()

            val usuarioActualizado = Usuarios(
                id_usuario = idUsuario,
                nombre = nombreNuevo,
                apPaterno = apPaternoNuevo,
                apMaterno = apMaternoNuevo,
                edad = edadNuevo,
                genero = generoNuevo,
                correo = correoNuevo,
                contrasena = contrasenaNueva,
                fechaNacimiento = fechaNacimientoNuevo
            )

            actualizarUsuario(idUsuario, usuarioActualizado)
        }
        val btnCancelar = findViewById<Button>(R.id.btnCancelar)
        btnCancelar.setOnClickListener {
            val intent = Intent(this, Menu_Activity::class.java)
            startActivity(intent)
        }
    }

    private fun obtenerGeneroSeleccionado(): String {
        val radioGroupGenero = findViewById<RadioGroup>(R.id.radioGenero)
        return when (radioGroupGenero.checkedRadioButtonId) {
            R.id.radioMasculino -> "Masculino"
            R.id.radioFemenino -> "Femenino"
            else -> "Otro"
        }
    }

    private fun actualizarUsuario(id: Int, usuario: Usuarios) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitClient.webService.actualizarUsuario(id, usuario)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@Acutalizar_Activity, "Actualización exitosa", Toast.LENGTH_LONG).show()
                        val intent = Intent(this@Acutalizar_Activity, Menu_Activity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@Acutalizar_Activity, "Fallo la actualización", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@Acutalizar_Activity, "Error de conexión: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun showDatePickerDialog(btnFechaNacimiento: Button) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->

            val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(selectedYear - 1900, selectedMonth, selectedDay))

            btnFechaNacimiento.text = formattedDate
        }, year, month, day)

        datePickerDialog.show()
    }
}
