package com.example.a02_m1_g2_kmp

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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.painterResource

import a02_m1_g2_kmp.composeapp.generated.resources.Res
import a02_m1_g2_kmp.composeapp.generated.resources.compose_multiplatform
import a02_m1_g2_kmp.composeapp.generated.resources.error
import a02_m1_g2_kmp.composeapp.generated.resources.my_key
import androidx.compose.foundation.clickable
import com.example.a02_m1_g2_kmp.presentation.ui.theme.AppTheme
import org.jetbrains.compose.resources.stringResource

@Composable
@Preview
fun App() {
    AppTheme {
        var showContent by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize()
            ,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(stringResource(Res.string.my_key))
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Image(painterResource(Res.drawable.error), null)

                }
            }
        }
    }
}