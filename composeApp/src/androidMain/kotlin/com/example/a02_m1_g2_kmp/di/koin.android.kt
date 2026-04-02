package com.example.a02_m1_g2_kmp.di

import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.a02_m1_g2_kmp.db.MyDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun databaseModule() = module {
    single {
        //Penser à faire un Build -> "Compile all Sources" pour générer le MyDatabase
        val driver = AndroidSqliteDriver(MyDatabase.Schema, get(), "test.db")
        MyDatabase(driver)
    }
}