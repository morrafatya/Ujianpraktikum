package com.example.mahasiswaapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mahasiswaapp.data.model.Mahasiswa
import com.example.mahasiswaapp.ui.viewmodel.MahasiswaViewModel

@Composable
fun FormScreen(
    navController: NavController,
    id: Int? = null,
    viewModel: MahasiswaViewModel
) {
    var nama by remember { mutableStateOf("") }
    var nim by remember { mutableStateOf("") }
    var jurusan by remember { mutableStateOf("") }
    var ttl by remember { mutableStateOf("") }
    var alamat by remember { mutableStateOf("") }
    var posisi by remember { mutableStateOf("") }
    var mitra by remember { mutableStateOf("") }

    val mahasiswa by viewModel.selectedMahasiswa.collectAsState()

    // Load data kalau edit
    LaunchedEffect(id) {
        if (id != null) {
            viewModel.getMahasiswaById(id)
        }
    }

    // Set default value jika data berhasil di-load
    LaunchedEffect(mahasiswa) {
        mahasiswa?.let {
            nama = it.nama
            nim = it.nim
            jurusan = it.jurusan
            ttl = it.tempat_tanggal_lahir
            alamat = it.alamat
            posisi = it.posisi
            mitra = it.mitra
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()) // supaya bisa scroll kalau field banyak
    ) {
        OutlinedTextField(
            value = nama,
            onValueChange = { nama = it },
            label = { Text("Nama") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = nim,
            onValueChange = { nim = it },
            label = { Text("NIM") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = jurusan,
            onValueChange = { jurusan = it },
            label = { Text("Jurusan") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = ttl,
            onValueChange = { ttl = it },
            label = { Text("Tempat Tanggal Lahir") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = alamat,
            onValueChange = { alamat = it },
            label = { Text("Alamat") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = posisi,
            onValueChange = { posisi = it },
            label = { Text("Posisi") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = mitra,
            onValueChange = { mitra = it },
            label = { Text("Mitra") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val data = Mahasiswa(
                    nama = nama,
                    nim = nim,
                    jurusan = jurusan,
                    tempat_tanggal_lahir = ttl,
                    alamat = alamat,
                    posisi = posisi,
                    mitra = mitra
                )
                if (id == null) {
                    viewModel.addMahasiswa(data)
                } else {
                    viewModel.updateMahasiswa(data, id)
                }
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (id == null) "Tambah Mahasiswa" else "Update Mahasiswa")
        }
    }
}
