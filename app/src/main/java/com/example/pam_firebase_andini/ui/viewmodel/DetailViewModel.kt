package com.example.pam_firebase_andini.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pam_firebase_andini.model.Mahasiswa
import com.example.pam_firebase_andini.navigation.DestinasiDetail
import com.example.pam_firebase_andini.repository.MahasiswaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

sealed class DetailUiState {
    object Error : DetailUiState()
    data class Success(val mahasiswa: Mahasiswa) : DetailUiState()
    object Loading : DetailUiState()
}

class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: MahasiswaRepository
) : ViewModel() {
    private val nim: String = checkNotNull(savedStateHandle[DestinasiDetail.NIM])
    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState

    init {
        getMahasiswaById()
    }

    fun getMahasiswaById() {
        viewModelScope.launch {
            _uiState.value = DetailUiState.Loading

            repository.getMahasiswaById(nim)
                .onStart {
                }
                .catch {
                    _uiState.value = DetailUiState.Error
                }
                .collect { mahasiswa ->
                    _uiState.value = DetailUiState.Success(mahasiswa)
                }
        }
    }
}
