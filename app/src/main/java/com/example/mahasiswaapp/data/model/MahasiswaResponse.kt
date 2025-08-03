package com.example.mahasiswaapp.data.model



data class MahasiswaResponse(
    val id: Int,
    val nama: String,
    val tempat_tanggal_lahir: String,
    val alamat: String,
    val nim: String,
    val jurusan: String,
    val posisi: String,
    val mitra: String,
    val status_acc: String
)
