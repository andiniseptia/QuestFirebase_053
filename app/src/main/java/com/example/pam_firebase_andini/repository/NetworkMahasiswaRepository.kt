package com.example.pam_firebase_andini.repository

import com.example.pam_firebase_andini.model.Mahasiswa
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class NetworkMahasiswaRepository(
    private val firestore: FirebaseFirestore
) : MahasiswaRepository {
    override suspend fun getMahasiswa(): Flow<List<Mahasiswa>> = callbackFlow { //callbackflow agar datanya realtime

        val mhsCollection = firestore.collection("Mahasiswa")
            .orderBy("nama", Query.Direction.ASCENDING)
            .addSnapshotListener { value, error -> //addSnapshotListener untuk data listener yang realtime

                if ( value != null ) { //Jika nilainya bukan null,
                    val mhsList = value.documents.mapNotNull { //Data akan dimasukkan ke dalam model
                        it.toObject(Mahasiswa::class.java)!!
                    }
                    trySend(mhsList) //try send memberikan fungsi untuk mengirim data ke flow
                }
            }
        awaitClose{
            mhsCollection.remove()
        }
    }

    override suspend fun insertMahasiswa(mahasiswa: Mahasiswa) {
        TODO("Not yet implemented")
    }

    override suspend fun updateMahasiswa(nim: String, mahasiswa: Mahasiswa) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMahasiswa(nim: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getMahasiswaById(nim: String): Flow<Mahasiswa> {
        TODO("Not yet implemented")
    }
}