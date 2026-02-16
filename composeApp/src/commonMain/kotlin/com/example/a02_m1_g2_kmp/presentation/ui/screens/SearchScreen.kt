package com.example.a02_m1_g2_kmp.presentation.ui.screens

import a02_m1_g2_kmp.composeapp.generated.resources.Res
import a02_m1_g2_kmp.composeapp.generated.resources.error
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.a02_m1_g2_kmp.App
import com.example.a02_m1_g2_kmp.data.remote.PhotographDTO
import com.example.a02_m1_g2_kmp.presentation.ui.theme.AppTheme
import com.example.a02_m1_g2_kmp.presentation.viewmodel.MainViewModel
import org.jetbrains.compose.resources.painterResource
import kotlin.repeat

@Preview(showBackground = true, showSystemUi = true)
//@Preview(showBackground = true, showSystemUi = true,
//           uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES or android.content.res.Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun SearchScreenPreview() {
    //Il faut remplacer NomVotreAppliTheme par le thème de votre application
    //Utilisé par exemple dans MainActivity.kt sous setContent {...}
    AppTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

            val mainViewModel = MainViewModel()
            mainViewModel.loadFakeData()

            SearchScreen(modifier = Modifier.padding(innerPadding), mainViewModel = mainViewModel)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
//@Preview(showBackground = true, showSystemUi = true,
//           uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES or android.content.res.Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun SearchScreenNoDataPreview() {
    //Il faut remplacer NomVotreAppliTheme par le thème de votre application
    //Utilisé par exemple dans MainActivity.kt sous setContent {...}
    AppTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

            val mainViewModel = MainViewModel()

            SearchScreen(modifier = Modifier.padding(innerPadding), mainViewModel = mainViewModel)
        }
    }
}

@Composable
fun SearchScreen(modifier: Modifier = Modifier, mainViewModel: MainViewModel = MainViewModel()) {
    Column  (modifier= modifier.fillMaxSize()) {
        val list = mainViewModel.dataList.value

        repeat(list.size){
            PictureRowItem(data = list[it])
        }
    }
}

@Composable //Composable affichant 1 élément
fun PictureRowItem(modifier: Modifier = Modifier, data: PhotographDTO) {
    Row(modifier = modifier.background(MaterialTheme.colorScheme.tertiary).fillMaxWidth()) {
//Permission Internet nécessaire
        AsyncImage(
            model = data.photoUrl,
            //Pour aller le chercher dans string.xml R de votre package com.nom.projet
            //contentDescription = getString(R.string.picture_of_cat),
            //En dur
            contentDescription = "une photo de chat",
            contentScale = ContentScale.FillWidth,

            //Pour toto.png. Si besoin de choisir l'import pour la classe R, c'est celle de votre package
            //Image d'échec de chargement qui sera utilisé par la preview
            error = painterResource(Res.drawable.error),
            //Image d'attente.
            //placeholder = painterResource(R.drawable.toto),

            onError = { println(it) },
            modifier = Modifier
                .heightIn(max = 100.dp)
                .widthIn(max = 100.dp)
        )

        Column {
            Text(text = data.stageName,fontSize = 20.sp)
            Text(text = data.story,fontSize = 14.sp)
        }

    }
}