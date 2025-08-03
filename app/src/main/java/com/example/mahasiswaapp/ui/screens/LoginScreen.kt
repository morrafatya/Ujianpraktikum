package com.example.mahasiswaapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mahasiswaapp.data.repository.AuthRepository
import com.example.mahasiswaapp.ui.viewmodel.AuthViewModel
import com.example.mahasiswaapp.ui.viewmodel.AuthViewModelFactory
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {

    // ✅ Inisialisasi ViewModel dengan Factory
    val authRepository = remember { AuthRepository() }
    val factory = remember { AuthViewModelFactory(authRepository) }
    val authViewModel: AuthViewModel = viewModel(factory = factory)

    // ✅ State untuk input form
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // ✅ Observasi state login dari ViewModel
    val loginState by authViewModel.loginState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Login") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            // ✅ Input Email
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // ✅ Input Password
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // ✅ Tombol Login
            Button(
                onClick = {
                    authViewModel.login(email, password)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login")
            }

            // 🔽 Tambahkan tombol teks “Belum punya akun?”
            TextButton(
                onClick = {
                    // Navigasi ke halaman register
                    navController.navigate("register")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Belum punya akun? Daftar di sini")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ✅ Tampilkan status login
            when {
                loginState?.isSuccess == true -> {
                    Text("✅ Login berhasil!", color = MaterialTheme.colorScheme.primary)
                    // TODO: Navigasi ke HomeScreen
                    navController.navigate("home")
                }
                loginState?.isFailure == true -> {
                    Text("❌ Login gagal: ${loginState?.exceptionOrNull()?.message}")
                }
                else -> {
                    // belum ada aksi, diam saja
                }
            }
        }
    }
}
