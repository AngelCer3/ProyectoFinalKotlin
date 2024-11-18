package com.example.proyectofinal

import com.google.gson.GsonBuilder
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

object AppConstantes{
    const val BASE_URL="http://10.0.2.2:3000" //Direccion ip de tu equipo de computa si lo emulas desde celular
                                          //Si desde el emulador de android es con la direccion ip establecida
}

interface WebService{
    @POST("/appAgregarUsuario")
    suspend fun agregarUsuarios(
        @Body usuarios: Usuarios
    ): Response<String>

    @GET("/appObtenerUsuarios")
    suspend fun obtenerDatos(): Response<List<Usuarios>>


    @POST("/appInicioSesion")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>

    @PUT("/appActualizarUsuario/{id}")
    suspend fun actualizarUsuario(
        @Path("id") userId: Int,
        @Body usuarios: Usuarios
    ): Response<String>

    @DELETE("/appEliminarUsuario/{id}")
    suspend fun eliminarUsuario(
        @Path("id") userId: Int
    ): Response<String>
}

object RetrofitClient{
    val webService : WebService by lazy {
        Retrofit.Builder()
            .baseUrl(AppConstantes.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(WebService::class.java)
    }
}