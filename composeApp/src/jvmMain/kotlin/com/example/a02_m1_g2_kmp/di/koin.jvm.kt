package com.example.a02_m1_g2_kmp.di

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.example.a02_m1_g2_kmp.db.MyDatabase
import org.koin.core.module.Module
import org.koin.dsl.module
import java.io.File

actual fun databaseModule(): Module = module {
    single {
        val dbFile = File("myDatabase.db")
        val driver = JdbcSqliteDriver("jdbc:sqlite:${dbFile.name}")
        if (!dbFile.exists()) {
            MyDatabase.Schema.create(driver)
        }
        MyDatabase(driver)
    }
}