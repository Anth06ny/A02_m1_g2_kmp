package com.example.a02_m1_g2_kmp.di

import com.example.a02_m1_g2_kmp.data.remote.KtorPhotographAPI
import com.example.a02_m1_g2_kmp.presentation.viewmodel.MainViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

//Si besoin du contexte, pour le passer en paramètre au lancement de Koin
fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(apiModule, viewModelModule, databaseModule())
    }.koin

// Version pour iOS et Desktop
fun initKoin() = initKoin {}

//------------------------
//DECLARATION DES MODULES
//------------------------
expect fun databaseModule(): Module

val apiModule = module {
    //Création d'un singleton pour le client HTTP
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true }, contentType = ContentType.Any)
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 5000
            }
        }
    }

    //Création d'un singleton pour les repository.
    //Get() injectera les objets déjà connus par koin, ici le HttpClient
    //single { KtorPhotographAPI(get()) }

    //Version avec injection automatique des objets connues
    singleOf(::KtorPhotographAPI)
}

//Version spécifique au ViewModel
val viewModelModule = module {

    //V1 : Si on veut ajouter manuellement certain paramètre
    //viewModel { MainViewModel(get(), Dispatchers.IO) }

    //V2 On déclare un Dispatchers à koin
    viewModelOf(::MainViewModel)
}