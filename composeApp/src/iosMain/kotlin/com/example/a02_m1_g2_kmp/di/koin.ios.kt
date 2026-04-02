package com.example.a02_m1_g2_kmp.di

import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.example.a02_m1_g2_kmp.db.MyDatabase
import org.koin.dsl.module

//koin.iosmain.kt (dans iosMain)
actual fun databaseModule() = module {
    single {
        val driver = NativeSqliteDriver(MyDatabase.Schema, "test.db")
        MyDatabase(driver)
    }
}