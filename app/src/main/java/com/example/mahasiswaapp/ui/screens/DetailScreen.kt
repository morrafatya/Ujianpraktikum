package com.example.mahasiswaapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mahasiswaapp.ui.viewmodel.MahasiswaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navController: NavController,
    id: Int,
    viewModel: MahasiswaViewModel
) {
    // 🔄 Ambil mahasiswa berdasarkan id dari ViewModel
    val mahasiswa by viewModel.selectedMahasiswa.collectAsState()

    // 🟢 Jalankan ketika screen dibuka untuk load data
    LaunchedEffect(id) {
        viewModel.getMahasiswaById(id)
    }

    // 🔧 UI Layout
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Detail Mahasiswa") })
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            if (mahasiswa == null) {
                // Kalau data belum ada atau gagal di-load
                Text(text = "Data tidak ditemukan atau sedang dimuat...")
            } else {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(text = "📛 Nama: ${mahasiswa!!.nama}", style = MaterialTheme.typography.titleMedium)
                    Text(text = "🎓 NIM: ${mahasiswa!!.nim}", style = MaterialTheme.typography.bodyLarge)
                    Text(text = "🏫 Jurusan: ${mahasiswa!!.jurusan}", style = MaterialTheme.typography.bodyLarge)

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("⬅ Kembali")
                    }
                }
            }
        }
    }
}
