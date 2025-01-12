package com.example.pam_firebase_andini.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pam_firebase_andini.model.Mahasiswa
import com.example.pam_firebase_andini.repository.MahasiswaRepository
import kotlinx.coroutines.launch
import java.text.Normalizer.Form


data class MahasiswaEvent(
    val nim: String = "",
    val nama: String = "",
    val jenis_kelamin: String = "",
    val alamat: String = "",
    val kelas: String = "",
    val angkatan: String = ""
)
