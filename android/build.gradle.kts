plugins {
    id("org.jetbrains.compose") version "1.0.0"
    id("com.android.application")
    kotlin("android")
}

group = "me.saine"
version = "1.0"

repositories {
    jcenter()
}

dependencies {
    implementation(project(":common"))
    implementation("androidx.activity:activity-compose:1.3.0")
    implementation("androidx.navigation:navigation-compose:2.4.1")
    implementation("io.coil-kt:coil-compose:2.0.0-rc01")
    implementation("com.google.firebase:firebase-auth-ktx:20.0.4")
    implementation("com.google.firebase:firebase-firestore-ktx:22.1.2")

}

android {
    compileSdkVersion(31)
    defaultConfig {
        applicationId = "me.saine.android"
        minSdkVersion(24)
        targetSdkVersion(31)
        versionCode = 1
        versionName = "1.0"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}