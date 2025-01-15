package com.example.pam_firebase_andini.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pam_firebase_andini.model.Mahasiswa
import com.example.pam_firebase_andini.navigation.DestinasiNavigasi
import com.example.pam_firebase_andini.ui.viewmodel.DetailUiState
import com.example.pam_firebase_andini.ui.viewmodel.DetailViewModel
import com.example.pam_firebase_andini.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailView(
    nim: String,
    onBack: () -> Unit = { },
    onEditClick: (String) -> Unit,
    viewModel: DetailViewModel = viewModel(factory = PenyediaViewModel.Factory),


) {
    val detailUiState by viewModel.uiState.collectAsState()
    viewModel.getMahasiswaById()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(top = 18.dp),
        topBar = {
            TopAppBar(
                title = { Text("Detail Mahasiswa") },
                navigationIcon = {
                    Button(onClick = onBack) {
                        Text("Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        val detailUiState by viewModel.uiState.collectAsState()

        BodyDetail(
            modifier = Modifier.padding(innerPadding),
            detailUiState = detailUiState,
        )
    }
}

@Composable
fun BodyDetail(
    modifier: Modifier = Modifier,
    detailUiState: DetailUiState,
) {
    when (detailUiState) {
        is DetailUiState.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is DetailUiState.Success -> {
            Column(modifier = modifier.fillMaxWidth().padding(16.dp)) {
                ItemDetailMahasiswa(mahasiswa = detailUiState.mahasiswa)

                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        is DetailUiState.Error -> {
            Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(text = "Error loading data")
            }
        }
    }
}

@Composable
fun ItemDetailMahasiswa( mahasiswa: Mahasiswa, modifier: Modifier = Modifier, ) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ComponentDetailMahasiswa(judul = "NIM", isinya = mahasiswa.nim)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailMahasiswa(judul = "Nama", isinya = mahasiswa.nama)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailMahasiswa(judul = "Alamat", isinya = mahasiswa.alamat)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailMahasiswa(judul = "Jenis Kelamin", isinya = mahasiswa.jenis_kelamin)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailMahasiswa(judul = "Kelas", isinya = mahasiswa.kelas)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailMahasiswa(judul = "Angkatan", isinya = mahasiswa.angkatan)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailMahasiswa(judul = "Judul Skripsi", isinya = mahasiswa.judul_skripsi)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailMahasiswa(judul = "Dosen pembimbing 1", isinya = mahasiswa.dosen1)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailMahasiswa(judul = "Dosen Pembimbing 2", isinya = mahasiswa.dosen2)
        }
    }
}

@Composable
fun ComponentDetailMahasiswa(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul : ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Text(
            text = isinya, fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.LightGray
        )
    }
}