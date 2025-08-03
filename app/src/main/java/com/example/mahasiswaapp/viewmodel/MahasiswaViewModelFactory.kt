package com.example.mahasiswaapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mahasiswaapp.data.repository.MahasiswaRepository

class MahasiswaViewModelFactory(private val repository: MahasiswaRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MahasiswaViewModel::class.java)) {
            return MahasiswaViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
