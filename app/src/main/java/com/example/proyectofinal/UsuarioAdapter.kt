package com.example.proyectofinal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UsuarioAdapter (
    private val listaUsuarios: MutableList<Usuarios>,
    private val onBorrarClick: (Usuarios) -> Unit,
    private val onEditarClick: (Usuarios) -> Unit
) : RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder>(){
    inner class UsuarioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvIdUsuario : TextView = itemView.findViewById(R.id.tvIdUsuario)
        val tvNombre: TextView = itemView.findViewById(R.id.tvNombre)
        val tvEdad: TextView = itemView.findViewById(R.id.tvEdad)
        val btnEditar: Button = itemView.findViewById(R.id.btnEditar)
        val btnEliminar: Button = itemView.findViewById(R.id.btnEliminar)

        fun bind(usuarios: Usuarios){
            tvIdUsuario.text = usuarios.id_usuario.toString()
            tvNombre.text = usuarios.nombre
            tvEdad.text = usuarios.edad

            btnEditar.setOnClickListener {
                onEditarClick(usuarios)
            }
            btnEliminar.setOnClickListener {
                onBorrarClick(usuarios)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_usuario,parent, false)
        return UsuarioViewHolder(view)
    }

    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
        holder.bind(listaUsuarios[position])
    }

    override fun getItemCount(): Int = listaUsuarios.size
}