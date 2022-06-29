import com.mhelrigo.buildsrc.*

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
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
}

dependencies {
    implementation(Kotlin.CORE_KTX)
    implementation(Kotlin.COROUTINES)

    // Room
    implementation(Room.RUNTIME)
    implementation(Room.TEST_HELPER)
    annotationProcessor(Room.COMPILER)

    // Firebase
    implementation(platform(Firebase.BOM))
    implementation(Firebase.DATABASE)
}