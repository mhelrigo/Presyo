import com.mhelrigo.buildsrc.*

plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("kotlin")
    id("kotlin-kapt")
}

configure<JavaPluginExtension> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    // Coroutine
    implementation(Kotlin.COROUTINES)
    implementation(Kotlin.COROUTINES_TEST)

    // Hilt
    implementation(Hilt.HILT_CORE)
    kapt(Hilt.HILT_ANDROID_COMPILER)

    // JUnit
    implementation(JUnit.J_UNIT)
    testImplementation(Mockito.MOCKITO)
}

kapt {
    correctErrorTypes = true
}