package com.mhelrigo.buildsrc

object AndroidBuild {
    const val APPLICATION_ID = "com.mhelrigo.presyo"
    const val COMPILED_SDK = 32
    const val TARGET_SDK = 32
    const val MIN_SDK = 21
    const val VERSION_CODE = 1
    const val VERSION_NAME = "0.0.0"
}

object Kotlin {
    val CORE_KTX by lazy { "androidx.core:core-ktx:1.7.0" }
    val COROUTINES by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9" }
}

object Room {
    private const val VERSION = "2.4.2"
    val RUNTIME by lazy { "androidx.room:room-runtime:$VERSION" }
    val COMPILER by lazy { "androidx.room:room-compiler:$VERSION" }
    val TEST_HELPER by lazy { "androidx.room:room-testing:$VERSION" }
}

object Firebase {
    val BOM by lazy { "com.google.firebase:firebase-bom:30.1.0" }
    val DATABASE by lazy { "com.google.firebase:firebase-database-ktx" }
}