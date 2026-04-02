package com.example.a02_m1_g2_kmp

import android.app.Application
import com.example.a02_m1_g2_kmp.di.initKoin
import org.koin.android.ext.koin.androidContext

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        //On démarre Koin avec le contexte
        initKoin { androidContext(this@MyApplication) }
    }
}