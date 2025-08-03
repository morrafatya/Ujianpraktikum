package com.example.mahasiswaapp.data.repository

import com.example.mahasiswaapp.data.api.RetrofitClient
import com.example.mahasiswaapp.data.model.Mahasiswa
import retrofit2.Response


class MahasiswaRepository {

    private val apiService = RetrofitClient.instance

    suspend fun getAllMahasiswa() = apiService.getMahasiswa()

    suspend fun getMahasiswaById(id: Int) = apiService.getMahasiswaById(id)

    suspend fun createMahasiswa(mahasiswa: Mahasiswa) = apiService.createMahasiswa(
        nama = mahasiswa.nama,
        nim = mahasiswa.nim,
        alamat = mahasiswa.alamat,
        mitra = mahasiswa.mitra,
        posisi = mahasiswa.posisi,
        ttl = mahasiswa.tempat_tanggal_lahir,
        jurusan = mahasiswa.jurusan
    )

    suspend fun updateMahasiswa(id: Int, mahasiswa: Mahasiswa) = apiService.updateMahasiswa(id,
        nama = mahasiswa.nama,
        nim = mahasiswa.nim,
        alamat = mahasiswa.alamat,
        mitra = mahasiswa.mitra,
        posisi = mahasiswa.posisi,
        ttl = mahasiswa.tempat_tanggal_lahir,
        jurusan = mahasiswa.jurusan)

//    suspend fun deleteMahasiswa(id: Int) = apiService.deleteMahasiswa(id)
suspend fun deleteMahasiswa(id: Int): Response<Void> {
    return apiService.deleteMahasiswa(id)
}

}
