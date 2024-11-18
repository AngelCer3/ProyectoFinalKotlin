package com.example.proyectofinal

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectofinal.databinding.ActivityMenuBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Menu_Activity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding
    private lateinit var adaptador: UsuarioAdapter
    private var listaUsuarios = arrayListOf<Usuarios>()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.itemRvUsuario.layoutManager = LinearLayoutManager(this)

        adaptador = UsuarioAdapter(listaUsuarios,
            onBorrarClick = { usuarios ->
                eliminarUsuario(usuarios.id_usuario)
            },
            onEditarClick = { usuarios ->
                val intent = Intent(this, Acutalizar_Activity::class.java). apply {
                    putExtra("id_usuario", usuarios.id_usuario)
                    putExtra("nombre", usuarios.nombre)
                    putExtra("apPaterno", usuarios.apPaterno)
                    putExtra("apMaterno", usuarios.apMaterno)
                    putExtra("edad", usuarios.edad)
                    putExtra("genero", usuarios.genero)
                    putExtra("correo", usuarios.correo)
                    putExtra("contrasena", usuarios.contrasena)
                    putExtra("fechaNacimiento", usuarios.fechaNacimiento)
                }
                startActivity(intent)
            }
        )
        binding.itemRvUsuario.adapter = adaptador

        obtenerUsuarios()

        binding.cierreSesion.setOnClickListener {
            cerrarSesion()
        }
    }

    private fun eliminarUsuario(userId: Int){
        CoroutineScope(Dispatchers.IO).launch {
            val call = RetrofitClient.webService.eliminarUsuario(userId)
            runOnUiThread {
                if (call.isSuccessful){
                    listaUsuarios.removeAll{it.id_usuario == userId}
                    adaptador.notifyDataSetChanged()
                    Toast.makeText(this@Menu_Activity, "Usuario Eliminado", Toast.LENGTH_LONG).show()
                    obtenerUsuarios()
                }
            }
        }
    }

    private fun obtenerUsuarios(){
        CoroutineScope(Dispatchers.IO).launch {
            val call = RetrofitClient.webService.obtenerDatos()
            runOnUiThread {
                if(call.isSuccessful && call.body()!=null){
                    listaUsuarios.clear()
                    listaUsuarios.addAll(call.body()!!)
                    adaptador.notifyDataSetChanged()
                }else{
                    Toast.makeText(this@Menu_Activity,"No hay registros", Toast.LENGTH_LONG).show()
                }
            }
        }
    }


    private fun cerrarSesion(){
        sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()

        val intent = Intent(this,MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
