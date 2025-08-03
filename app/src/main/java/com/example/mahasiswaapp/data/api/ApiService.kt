package com.example.mahasiswaapp.data.api

import com.example.mahasiswaapp.data.DaftarMahasiswaResponse
import com.example.mahasiswaapp.data.model.LoginResponse
import com.example.mahasiswaapp.data.model.Mahasiswa
import com.example.mahasiswaapp.data.model.MahasiswaResponse
import com.example.mahasiswaapp.data.model.RegisterResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("/api/login")
    suspend fun login(
        @Field("login") email: String,
        @Field("password") password: String
    ): Response<LoginResponse>
//
//    @FormUrlEncoded
//    @POST("/api/register")
//    suspend fun register(
//        @Field("name") name: String,
//        @Field("email") email: String,
//        @Field("password") password: String
//    ): Response<RegisterResponse>

    @FormUrlEncoded
    @POST("/api/register")
    suspend fun register(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("pass_confirm") passConfirm: String
    ): Response<RegisterResponse>

    @GET("daftarmahasiswa")
    suspend fun getMahasiswa(): Response<DaftarMahasiswaResponse>

    @GET("daftarmahasiswa/{id}")
    suspend fun getMahasiswaById(
        @Path("id") id: Int
    ): Response<Mahasiswa>

    @FormUrlEncoded
    @POST("daftarmahasiswa/store")
    suspend fun createMahasiswa(
        @Field("nama") nama: String,
        @Field("tempat_tanggal_lahir") ttl: String,
        @Field("alamat") alamat: String,
        @Field("posisi") posisi: String,
        @Field("mitra") mitra: String,
        @Field("nim") nim: String,
        @Field("jurusan") jurusan: String
    ): Response<Mahasiswa>

    @FormUrlEncoded
    @POST("daftarmahasiswa/update/{id}")
    suspend fun updateMahasiswa(
        @Path("id") id: Int,
        @Field("nama") nama: String,
        @Field("tempat_tanggal_lahir") ttl: String,
        @Field("alamat") alamat: String,
        @Field("posisi") posisi: String,
        @Field("mitra") mitra: String,
        @Field("nim") nim: String,
        @Field("jurusan") jurusan: String
    ): Response<Mahasiswa>

    @POST("daftarmahasiswa/delete/{id}")
    suspend fun deleteMahasiswa(@Path("id") id: Int): Response<Void>
}
