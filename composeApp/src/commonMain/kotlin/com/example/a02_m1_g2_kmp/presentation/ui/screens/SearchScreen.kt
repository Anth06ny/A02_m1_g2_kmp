package com.example.a02_m1_g2_kmp.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a02_m1_g2_kmp.App
import com.example.a02_m1_g2_kmp.presentation.ui.theme.AppTheme
import com.example.a02_m1_g2_kmp.presentation.viewmodel.MainViewModel
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
    Column  (modifier= modifier) {
        println("SearchScreen()")
        Text(text = "Text1",fontSize = 20.sp, modifier = Modifier.padding(start = 8.dp).background(Color.Blue)

            .clickable{

        })
        Spacer(Modifier.size(8.dp))
        Text(text = "Text2",fontSize = 14.sp)

        val list = mainViewModel.dataList.value

        repeat(list.size){
            PictureRowItem("text${list[it].stageName}")
        }
    }
}

@Composable
fun PictureRowItem(text:String){
    Text(text = text,fontSize = 14.sp)
}