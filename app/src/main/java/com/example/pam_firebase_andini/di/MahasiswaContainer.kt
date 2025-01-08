package com.example.pam_firebase_andini.di

import com.example.pam_firebase_andini.repository.MahasiswaRepository
import com.example.pam_firebase_andini.repository.NetworkMahasiswaRepository
import com.google.firebase.firestore.FirebaseFirestore

interface AppContainer {
    val mahasiswaRepository: MahasiswaRepository
}

class MahasiswaContainer: AppContainer {
    private val firestore : FirebaseFirestore = FirebaseFirestore.getInstance() //Setara dengan url di remote db
    override val mahasiswaRepository: MahasiswaRepository by lazy {
        NetworkMahasiswaRepository(firestore)
    }
}