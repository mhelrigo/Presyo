import com.mhelrigo.buildsrc.*
import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")

}

android {
    compileSdk = AndroidBuild.COMPILED_SDK

    defaultConfig {
        applicationId = AndroidBuild.APPLICATION_ID
        minSdk = AndroidBuild.MIN_SDK
        targetSdk = AndroidBuild.TARGET_SDK
        versionCode = AndroidBuild.VERSION_CODE
        versionName = AndroidBuild.VERSION_NAME

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))

    implementation(Kotlin.CORE_KTX)
    implementation("androidx.appcompat:appcompat:1.4.2")
    implementation("com.google.android.material:material:1.5.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    testImplementation(JUnit.J_UNIT)
    androidTestImplementation(JUnit.J_UNIT_EXT)
    androidTestImplementation(Espresso.ESPRESSO)
    implementation("androidx.activity:activity-ktx:1.5.0")

    implementation(Kotlin.COROUTINES)
    testImplementation(Kotlin.COROUTINES_TEST)

    implementation(platform(Firebase.BOM))
    implementation(Firebase.DATABASE)

    // Logging
    implementation(Timber.TIMBER)

    // Hilt
    implementation(Hilt.HILT_ANDROID)
    kapt(Hilt.HILT_ANDROID_COMPILER)
}

kapt {
    correctErrorTypes = true
}
