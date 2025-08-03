package com.example.mahasiswaapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mahasiswaapp.ui.viewmodel.MahasiswaViewModel
import com.example.mahasiswaapp.data.model.Mahasiswa

// Warna pink kustom
val PinkColor = Color(0xFFE91E63)

@Composable
fun HomeScreen(navController: NavController, viewModel: MahasiswaViewModel) {
    val mahasiswaList by viewModel.mahasiswaList.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    var selectedMahasiswaId by remember { mutableStateOf<Int?>(null) }

    LaunchedEffect(Unit) {
        viewModel.fetchMahasiswa()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(
            onClick = { navController.navigate("form") },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = PinkColor)
        ) {
            Text("Tambah Mahasiswa", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(mahasiswaList) { mahasiswa ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Nama: ${mahasiswa.nama}")
                        Text(text = "NIM: ${mahasiswa.nim}")
                        Text(text = "Jurusan: ${mahasiswa.jurusan}")
                        Spacer(modifier = Modifier.height(4.dp))

                        Text(text = "TTL: ${mahasiswa.tempat_tanggal_lahir}")
                        Text(text = "Alamat: ${mahasiswa.alamat}")
                        Text(text = "Posisi: ${mahasiswa.posisi}")
                        Text(text = "Mitra: ${mahasiswa.mitra}")

                        Spacer(modifier = Modifier.height(8.dp))

                        Row {


                            Spacer(modifier = Modifier.width(8.dp))

                            Button(
                                onClick = {
                                    mahasiswa.id?.let {
                                        viewModel.selectMahasiswa(mahasiswa)
                                        navController.navigate("form/$it")
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = PinkColor)
                            ) {
                                Text("Edit", color = Color.White)
                            }

                            Spacer(modifier = Modifier.width(8.dp))


                            Spacer(modifier = Modifier.width(8.dp))

                            Button(
                                onClick = {
                                    selectedMahasiswaId = mahasiswa.id
                                    showDialog = true
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = PinkColor)
                            ) {
                                Text("Hapus", color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                showDialog = false
                selectedMahasiswaId = null
            },
            title = { Text("Konfirmasi Hapus", color = PinkColor) },
            text = { Text("Apakah Anda yakin ingin menghapus data mahasiswa ini?") },
            confirmButton = {
                TextButton(onClick = {
                    selectedMahasiswaId?.let {
                        viewModel.deleteMahasiswa(it)
                    }
                    showDialog = false
                }) {
                    Text("Ya", color = PinkColor)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDialog = false
                    selectedMahasiswaId = null
                }) {
                    Text("Batal", color = PinkColor)
                }
            }
        )
    }
}
