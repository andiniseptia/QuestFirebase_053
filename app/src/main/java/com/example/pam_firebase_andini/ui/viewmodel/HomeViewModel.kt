package com.example.pam_firebase_andini.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pam_firebase_andini.model.Mahasiswa
import com.example.pam_firebase_andini.repository.MahasiswaRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

sealed class HomeUiState {
    data class Success(val mahasiswa: List<Mahasiswa>) : HomeUiState()
    data class Error(val message: Throwable): HomeUiState()
    object Loading: HomeUiState()
}

class HomeViewModel(private val mhs: MahasiswaRepository) : ViewModel() {
    //mhsUIState adalah kondisi
    var mhsUIState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getMhs()
    }

    fun getMhs() {
        viewModelScope.launch {
           mhs.getMahasiswa()
               .onStart {
                   //Ketika dimulai, kondisi/uistate diset ke loading
                   mhsUIState = HomeUiState.Loading
               }
               .catch {
                   //Kalau error, akan menampilkan error
                   mhsUIState = HomeUiState.Error(it)
               }
               .collect{
                   mhsUIState = if (it.isEmpty()){
                       HomeUiState.Error(Exception("Belum ada daftar mahasiswa."))
                   }
                   else{
                       HomeUiState.Success(it)
                   }
               }
        }
    }

    fun deleteMahasiswa(mahasiswa: Mahasiswa) {
        viewModelScope.launch {
            try {
                mhs.deleteMahasiswa(mahasiswa)
            } catch (e: Exception) {
                mhsUIState = HomeUiState.Error(e)
            }
        }
    }
}