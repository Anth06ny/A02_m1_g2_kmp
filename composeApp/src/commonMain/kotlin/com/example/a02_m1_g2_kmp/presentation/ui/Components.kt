package com.example.a02_m1_g2_kmp.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun PictureGallery(modifier: Modifier = Modifier, urlList: List<String>)