
plugins {
    id("org.jetbrains.compose") version "1.0.0"
    id("com.android.application")
    id("com.google.gms.google-services")
    kotlin("android")
    id("kotlin-android")

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

    //Firebase

    implementation("com.google.firebase:firebase-auth-ktx:20.0.4")
    implementation("com.google.firebase:firebase-firestore-ktx:22.1.2")
    implementation("com.google.firebase:firebase-analytics:17.5.0")
    implementation("com.google.firebase:firebase-firestore:21.6.0")
    implementation(platform("com.google.firebase:firebase-bom:28.4.1"))
    implementation("com.android.support:multidex:1.0.3")
    implementation("com.google.firebase:firebase-database-ktx:20.0.3")
    implementation("com.firebaseui:firebase-ui-storage:7.2.0")
    implementation("com.google.android.gms:play-services-auth:20.1.0")


    implementation("com.google.firebase:firebase-storage-ktx:20.0.0")
    implementation("com.google.android.material:material:1.5.0")
    implementation("androidx.compose.ui:ui:${rootProject.extra["compose_version"]}")
    implementation("androidx.compose.material:material:${rootProject.extra["compose_version"]}")
    implementation("androidx.compose.ui:ui-tooling-preview:${rootProject.extra["compose_version"]}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:${rootProject.extra["compose_version"]}")
    debugImplementation("androidx.compose.ui:ui-tooling:${rootProject.extra["compose_version"]}")

    //Others
    //implementation("io.getstream:stream-chat-android-ui-components:4.8.1")


    //Tests


}

android {
    compileSdkVersion(31)
    defaultConfig {
        applicationId = "me.saine.android"
        minSdkVersion(24)
        targetSdkVersion(31)
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true
        vectorDrawables {
            useSupportLibrary = true
        }


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
    kotlinOptions {
        jvmTarget = "1.8"
        useIR = true
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = rootProject.extra["compose_version"] as String
        kotlinCompilerVersion = "1.5.10"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}