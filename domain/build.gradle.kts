plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("kotlin")
    id("kotlin-kapt")
}

configure<JavaPluginExtension> {
    sourceCompatibility = JavaVersion.VERSION_1_7
    targetCompatibility = JavaVersion.VERSION_1_7
}

dependencies {
    implementation(com.mhelrigo.buildsrc.Kotlin.COROUTINES)
}