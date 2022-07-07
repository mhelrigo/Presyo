import com.mhelrigo.buildsrc.*

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-android")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = AndroidBuild.COMPILED_SDK

    defaultConfig {
        minSdk = AndroidBuild.MIN_SDK
        targetSdk = AndroidBuild.TARGET_SDK

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
    packagingOptions {
        resources.pickFirsts.add("META-INF/AL2.0")
        resources.pickFirsts.add("META-INF/LGPL2.1")
    }
}

dependencies {
    implementation(project(":domain"))
    androidTestImplementation("androidx.test.ext:junit-ktx:1.1.3")

    // Coroutine
    implementation(Kotlin.CORE_KTX)
    implementation(Kotlin.COROUTINES)
    testImplementation(Kotlin.COROUTINES_TEST)

    // Room
    implementation(Room.RUNTIME)
    implementation(Room.TEST_HELPER)
    implementation(Room.KT_SUPPORT)
    kapt(Room.COMPILER)

    // Firebase
    implementation(platform(Firebase.BOM))
    implementation(Firebase.DATABASE)

    // Logging
    implementation(Timber.TIMBER)

    // Hilt
    implementation(Hilt.HILT_ANDROID)
    kapt(Hilt.HILT_ANDROID_COMPILER)

    // JUnit
    implementation(JUnit.J_UNIT)
    androidTestImplementation(JUnit.J_UNIT_EXT)
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    testImplementation(Mockito.MOCKITO)
}

kapt {
    correctErrorTypes = true
}
