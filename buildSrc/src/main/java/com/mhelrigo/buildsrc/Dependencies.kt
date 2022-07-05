package com.mhelrigo.buildsrc

object AndroidBuild {
    const val APPLICATION_ID = "com.mhelrigo.presyo"
    const val COMPILED_SDK = 32
    const val TARGET_SDK = 32
    const val MIN_SDK = 21
    const val VERSION_CODE = 1
    const val VERSION_NAME = "1.0.0"
}

object Kotlin {
    val CORE_KTX by lazy { "androidx.core:core-ktx:1.7.0" }
    val COROUTINES by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9" }
    val COROUTINES_TEST by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.9" }
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

object Timber {
    val TIMBER by lazy { "com.jakewharton.timber:timber:5.0.1" }
}

object Hilt {
    private const val VERSION = "2.38.1"
    val HILT_ANDROID by lazy { "com.google.dagger:hilt-android:$VERSION" }
    val HILT_ANDROID_COMPILER by lazy { "com.google.dagger:hilt-android-compiler:$VERSION" }
    val HILT_CORE by lazy { "com.google.dagger:hilt-core:$VERSION" }
}

object JUnit {
    val J_UNIT by lazy { "junit:junit:4.13.2" }
    val J_UNIT_EXT by lazy { "androidx.test.ext:junit:1.1.3" }
}

object Mockito {
    val MOCKITO by lazy { "org.mockito:mockito-core:1.10.19" }
}