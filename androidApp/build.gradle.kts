plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    kotlin("plugin.serialization") version "1.4.21"
}

android {
    compileSdk = 31
    defaultConfig {
        applicationId = "romanyuk.povarishka.android"
        minSdk = 26
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.1.0-beta04"
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildTypes { getByName("release") { isMinifyEnabled = false } }
}

val androidxVersion = "1.4.0"
val lifecycleVersion = "2.4.0"
val composeVersion = "1.0.5"
val koinVersion = "3.1.4"

dependencies {
    implementation(project(":shared"))
    implementation("androidx.appcompat:appcompat:$androidxVersion")
    implementation("androidx.fragment:fragment-ktx:$androidxVersion")
    implementation("androidx.activity:activity-ktx:$androidxVersion")
    implementation("androidx.activity:activity-compose:$androidxVersion")

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycleVersion")
    kapt("androidx.lifecycle:lifecycle-compiler:$lifecycleVersion")

    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling:$composeVersion")
    implementation("androidx.compose.foundation:foundation:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.compose.material:material-icons-core:$composeVersion")
    implementation("androidx.compose.material:material-icons-extended:$composeVersion")
    implementation("androidx.compose.runtime:runtime-livedata:$composeVersion")
    // Coil
    implementation("io.coil-kt:coil-compose:1.4.0")
    // Koin
    implementation("io.insert-koin:koin-android:$koinVersion")
}
