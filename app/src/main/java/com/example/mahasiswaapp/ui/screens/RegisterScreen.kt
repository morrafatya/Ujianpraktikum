package com.example.mahasiswaapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mahasiswaapp.data.repository.AuthRepository
import com.example.mahasiswaapp.ui.viewmodel.AuthViewModel
import com.example.mahasiswaapp.ui.viewmodel.AuthViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavController) {
    val context = LocalContext.current
    val authRepository = remember { AuthRepository() }
    val factory = remember { AuthViewModelFactory(authRepository) }
    val authViewModel: AuthViewModel = viewModel(factory = factory)

    var username  by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }

    val registerState by authViewModel.registerState.collectAsState()

    // ✅ Navigasi ke login saat registrasi berhasil & reset state
    LaunchedEffect(registerState?.isSuccess) {
        if (registerState?.isSuccess == true) {
            Toast.makeText(context, "Registrasi berhasil", Toast.LENGTH_SHORT).show()
            authViewModel.resetRegisterState() // <--- Penting untuk reset state!
            navController.navigate("login") {
                popUpTo("register") { inclusive = true } // remove register from backstack
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Register") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            TextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("username") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            TextField(
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                    passwordError = ""
                },
                label = { Text("Konfirmasi Password") },
                modifier = Modifier.fillMaxWidth(),
                isError = passwordError.isNotEmpty()
            )

            if (passwordError.isNotEmpty()) {
                Text(
                    text = passwordError,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (password != confirmPassword) {
                        passwordError = "Password dan konfirmasi tidak cocok"
                    } else {
                        passwordError = ""
                        authViewModel.register(username, email, password, confirmPassword)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Register")
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (registerState?.isFailure == true) {
                Text(
                    text = "❌ Registrasi gagal: ${registerState?.exceptionOrNull()?.message}",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
