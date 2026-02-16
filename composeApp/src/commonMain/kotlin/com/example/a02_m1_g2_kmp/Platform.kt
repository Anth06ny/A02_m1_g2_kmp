package com.example.a02_m1_g2_kmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform