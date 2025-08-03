package com.example.mahasiswaapp.data.repository

import com.example.mahasiswaapp.data.api.RetrofitClient
import com.example.mahasiswaapp.data.model.RegisterResponse
import retrofit2.Response


class AuthRepository {

    private val apiService = RetrofitClient.instance

    // ✅ Login (langsung kirim email & password)
    suspend fun login(email: String, password: String) =
        apiService.login(email, password)

    // ✅ Register (langsung kirim name, email & password)
    suspend fun register(username: String, email: String, password: String, passConfirm: String) =
        apiService.register(username, email, password, passConfirm)



}
