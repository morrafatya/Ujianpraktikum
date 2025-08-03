package com.example.mahasiswaapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mahasiswaapp.data.model.Mahasiswa
import com.example.mahasiswaapp.data.model.MahasiswaResponse
import com.example.mahasiswaapp.data.repository.MahasiswaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MahasiswaViewModel(private val repository: MahasiswaRepository) : ViewModel() {

    private val _mahasiswaList = MutableStateFlow<List<MahasiswaResponse>>(emptyList())
    val mahasiswaList: StateFlow<List<MahasiswaResponse>> get() = _mahasiswaList

    private val _selectedMahasiswa = MutableStateFlow<Mahasiswa?>(null)
    val selectedMahasiswa: StateFlow<Mahasiswa?> get() = _selectedMahasiswa

    private val _operationState = MutableStateFlow<Result<String>?>(null)
    val operationState: StateFlow<Result<String>?> get() = _operationState

    /**
     * Ambil semua data mahasiswa dari API
     */
    fun fetchMahasiswa() {
        viewModelScope.launch {
            try {
                val response = repository.getAllMahasiswa()
                if (response.isSuccessful && response.body() != null) {
                    _mahasiswaList.value = response.body()!!.daftarmahasiswa
                } else {
                    // Handle error, misalnya tampilkan toast atau log
                }
            } catch (e: Exception) {
                // Handle exception, misalnya tampilkan toast atau log
            }
        }
    }


    /**
     * Ambil data mahasiswa berdasarkan ID
     */
    fun getMahasiswaById(id: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getMahasiswaById(id)   // ✅ Ambil response dari API
                if (response.isSuccessful) {
                    _selectedMahasiswa.value = response.body()
                } else {
                    _selectedMahasiswa.value = null
                    _operationState.value = Result.failure(Exception("Mahasiswa tidak ditemukan"))
                }
            } catch (e: Exception) {
                _selectedMahasiswa.value = null
                _operationState.value = Result.failure(e)
            }
        }
    }

    /**
     * Tambahkan data mahasiswa
     */
    fun addMahasiswa(mahasiswa: Mahasiswa) {
        viewModelScope.launch {
            try {
                val response = repository.createMahasiswa(mahasiswa)
                if (response.isSuccessful) {
                    fetchMahasiswa()
                    _operationState.value = Result.success("Data berhasil ditambahkan")
                } else {
                    _operationState.value = Result.failure(Exception("Gagal menambah data: ${response.message()}"))
                }
            } catch (e: Exception) {
                _operationState.value = Result.failure(e)
            }
        }
    }

    /**
     * Update data mahasiswa
     */
    fun updateMahasiswa(mahasiswa: Mahasiswa,id: Int) {
        viewModelScope.launch {
            try {
                val response = repository.updateMahasiswa(id , mahasiswa)
                if (response.isSuccessful) {
                    fetchMahasiswa()
                    _operationState.value = Result.success("Data berhasil diperbarui")
                } else {
                    _operationState.value = Result.failure(Exception("Gagal memperbarui data: ${response.message()}"))
                }
            } catch (e: Exception) {
                _operationState.value = Result.failure(e)
            }
        }
    }

    /**
     * Hapus data mahasiswa
     */


    // ✅ Jadi:
    fun deleteMahasiswa(id: Int) {
        viewModelScope.launch {
            try {
                val response = repository.deleteMahasiswa(id)
                if (response.isSuccessful) {
                    fetchMahasiswa() // ✅ yang benar
                    _operationState.value = Result.success("Data berhasil dihapus")
                } else {
                    Log.e("Delete", "Gagal menghapus data: ${response.code()}")
                    _operationState.value = Result.failure(Exception("Gagal menghapus data: ${response.message()}"))
                }
            } catch (e: Exception) {
                Log.e("Delete", "Error: ${e.message}")
                _operationState.value = Result.failure(e)
            }
        }
    }

    // Tambahkan di dalam MahasiswaViewModel
    fun selectMahasiswa(mahasiswaResponse: MahasiswaResponse) {
        _selectedMahasiswa.value = Mahasiswa(
            nama = mahasiswaResponse.nama,
            tempat_tanggal_lahir = mahasiswaResponse.tempat_tanggal_lahir,
            alamat = mahasiswaResponse.alamat,
            posisi = mahasiswaResponse.posisi,
            mitra = mahasiswaResponse.mitra,
            nim = mahasiswaResponse.nim,
            jurusan = mahasiswaResponse.jurusan
        )
    }


}
