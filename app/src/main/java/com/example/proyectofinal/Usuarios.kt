package com.example.proyectofinal

data class Usuarios (
    var id_usuario: Int,
    var nombre: String,
    var apPaterno: String,
    var apMaterno: String,
    var edad: String,
    var genero: String,
    var correo: String,
    var contrasena: String,
    var fechaNacimiento: String
)