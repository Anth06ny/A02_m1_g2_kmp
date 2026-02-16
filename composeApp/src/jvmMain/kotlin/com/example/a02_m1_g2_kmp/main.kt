package com.example.a02_m1_g2_kmp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "A02_m1_g2_kmp",
    ) {
        App()
    }
}