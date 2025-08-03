package com.example.mahasiswaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mahasiswaapp.data.repository.MahasiswaRepository
import com.example.mahasiswaapp.ui.navigation.AppNavigation
import com.example.mahasiswaapp.ui.theme.MahasiswaappTheme // ← Tambahkan import ini
import com.example.mahasiswaapp.ui.viewmodel.MahasiswaViewModel
import com.example.mahasiswaapp.ui.viewmodel.MahasiswaViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: MahasiswaViewModel = viewModel(
                factory = MahasiswaViewModelFactory(MahasiswaRepository())
            )

            // Bungkus di sini agar tema pink aktif
            MahasiswaappTheme {
                AppNavigation(viewModel = viewModel)
            }
        }
    }
}
