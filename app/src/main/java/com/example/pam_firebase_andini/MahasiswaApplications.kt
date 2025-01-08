package com.example.pam_firebase_andini

import android.app.Application
import com.example.pam_firebase_andini.di.AppContainer
import com.example.pam_firebase_andini.di.MahasiswaContainer

class MahasiswaApplications: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container= MahasiswaContainer()
    }
}