package com.example.mahasiswaapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mahasiswaapp.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _loginState = MutableStateFlow<Result<String>?>(null)
    val loginState: StateFlow<Result<String>?> get() = _loginState

    private val _registerState = MutableStateFlow<Result<String>?>(null)
    val registerState: StateFlow<Result<String>?> get() = _registerState

    // ✅ Fungsi login
    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repository.login(email, password)
                if (response.isSuccessful) {
                    _loginState.value = Result.success("Login berhasil")
                } else {
                    _loginState.value = Result.failure(Exception("Login gagal"))
                }
            } catch (e: Exception) {
                _loginState.value = Result.failure(e)
            }
        }
    }

    // ✅ Fungsi register
    fun register(username: String, email: String, password: String, passConfirm: String) {
        viewModelScope.launch {
            try {
                val response = repository.register(username, email, password, passConfirm)
                if (response.isSuccessful) {
                    _registerState.value = Result.success("Registrasi berhasil")
                } else {
                    _registerState.value = Result.failure(Exception("Registrasi gagal: ${response.errorBody()?.string()}"))
                }
            } catch (e: Exception) {
                _registerState.value = Result.failure(e)
            }
        }
    }

    fun resetRegisterState() {
        _registerState.value = null
    }

}
