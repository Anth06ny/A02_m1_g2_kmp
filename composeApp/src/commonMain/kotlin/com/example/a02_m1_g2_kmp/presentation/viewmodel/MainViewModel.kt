package com.example.a02_m1_g2_kmp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a02_m1_g2_kmp.data.remote.KtorPhotographAPI
import com.example.a02_m1_g2_kmp.data.remote.PhotographDTO
import com.example.a02_m1_g2_kmp.db.MyDatabase
import com.example.a02_m1_g2_kmp.di.initKoin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

fun main() {
    val koin = initKoin()
    val mainViewModel = koin.get<MainViewModel>()
    mainViewModel.loadPhotographers()

//    withContext(Dispatchers.Default){
//
//        launch {
//            mainViewModel.errorMessage.collect {
//                println("La liste à changé")
//            }
//        }
//    }


    println("Liste des photographes : ${mainViewModel.dataList.value}")
    println("Erreur : ${mainViewModel.errorMessage.value}")

}

class MainViewModel(val ktorPhotographAPI: KtorPhotographAPI, val myDatabase: MyDatabase) : ViewModel() {

    private val _dataList = MutableStateFlow(emptyList<PhotographDTO>())
    val dataList = _dataList.asStateFlow()

    private val _runInProgress = MutableStateFlow(false)
    val runInProgress = _runInProgress.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage = _errorMessage.asStateFlow()

    private val photographerQueries = myDatabase.photographerQueries

    private val jsonParser = Json { prettyPrint = true }

    init {
        loadFakeData()
    }

    fun loadPhotographers() {

        _runInProgress.value = true

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val list = ktorPhotographAPI.loadPhotographs()

                //sauvegarde en base
                photographerQueries.transaction {
                    list.forEach {
                        photographerQueries.insertOrReplacePhotographer(
                            it.id.toLong(),
                            it.stageName,
                            it.photoUrl,
                            it.story,
                            jsonParser.encodeToString(it.portfolio))
                    }
                }

                _dataList.value = list
            } catch (e: Exception) {
                //afficher les log de l'erreur dans la console
                e.printStackTrace()
                _errorMessage.value = e.message ?: "Une erreur est survenue"

                _dataList.value = photographerQueries.selectAllPhotographers().executeAsList().map {
                    PhotographDTO(
                        it.id.toInt(),
                        it.stageName,
                        it.photoUrl,
                        it.story,
                        jsonParser.decodeFromString(it.portfolio)
                    )
                }
            }
            _runInProgress.value = false
        }

        println("Fin loadPhotographers()")

    }

    //Pour la Preview
    fun loadFakeData(runInProgress: Boolean = false, errorMessage: String = "") {
        _runInProgress.value = runInProgress
        _errorMessage.value = errorMessage
        _dataList.value = listOf(
            PhotographDTO(
                id = 1,
                stageName = "Bob la Menace",
                photoUrl = "https://www.amonteiro.fr/img/fakedata/bob.jpg",
                story = "Ancien agent secret, Bob a troqué ses gadgets pour un appareil photo après une mission qui a mal tourné. Il traque désormais les instants volés plutôt que les espions.",
                portfolio = listOf(
                    "https://example.com/photo1.jpg",
                    "https://example.com/photo2.jpg",
                    "https://example.com/photo3.jpg"
                )
            ),
            PhotographDTO(
                id = 2,
                stageName = "Jean-Claude Flash",
                photoUrl = "https://www.amonteiro.fr/img/fakedata.com/jc.jpg",
                story = "Ancien champion de rodéo, il s’est reconverti en photographe après une chute mémorable. Maintenant, il dompte la lumière comme un vrai cow-boy.",
                portfolio = listOf(
                    "https://picsum.photos/407",
                    "https://picsum.photos/125",
                    "https://picsum.photos/549"
                )
            )
        )
    }
}