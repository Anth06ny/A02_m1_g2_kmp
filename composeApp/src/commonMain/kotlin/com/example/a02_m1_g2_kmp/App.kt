package com.example.a02_m1_g2_kmp

import a02_m1_g2_kmp.composeapp.generated.resources.Res
import a02_m1_g2_kmp.composeapp.generated.resources.compose_multiplatform
import a02_m1_g2_kmp.composeapp.generated.resources.error
import a02_m1_g2_kmp.composeapp.generated.resources.my_key
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.a02_m1_g2_kmp.presentation.ui.screens.SearchScreen
import com.example.a02_m1_g2_kmp.presentation.ui.theme.AppTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
@Preview(
    showBackground = true,
    widthDp = 320,
    name = "Dark",
    showSystemUi = true
)
@Preview(showBackground = true, widthDp = 320)
fun App() {
    AppTheme {
        SearchScreen()
//        var showContent by remember { mutableStateOf(false) }
//        Column(
//            modifier = Modifier
//                .background(MaterialTheme.colorScheme.primaryContainer)
//                .safeContentPadding()
//                .fillMaxSize(),
//            horizontalAlignment = Alignment.CenterHorizontally,
//        ) {
//            Button(onClick = { showContent = !showContent }) {
//                Text("Click me!")
//            }
//            AnimatedVisibility(showContent) {
//                val greeting = remember { Greeting().greet() }
//                Column(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                ) {
//                    Text(stringResource(Res.string.my_key))
//                    Image(painterResource(Res.drawable.compose_multiplatform), null)
//                    Image(painterResource(Res.drawable.error), null)
//
//                }
//            }
//        }
    }
}