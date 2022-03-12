
plugins {
    id("org.jetbrains.compose") version "1.0.0"
    id("com.android.application")
    id("com.google.gms.google-services")
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

    //Firebase

    implementation("com.google.firebase:firebase-auth-ktx:20.0.4")
    implementation("com.google.firebase:firebase-firestore-ktx:22.1.2")
    implementation("com.google.firebase:firebase-analytics:17.5.0")
    implementation("com.google.firebase:firebase-firestore:21.6.0")
    implementation(platform("com.google.firebase:firebase-bom:28.4.1"))
    implementation("com.android.support:multidex:1.0.3")
    implementation("com.google.firebase:firebase-database-ktx:20.0.3")
    implementation("com.firebaseui:firebase-ui-storage:7.2.0")
    //implementation("com.google.firebase:firebase-database-ktx")

    implementation("com.google.firebase:firebase-storage-ktx:20.0.0")
    /*implementation("com.google.firebase:firebase-crashlytics-ktx")*/
    /*implementation("com.google.firebase:firebase-analytics-ktx")*/

    //Others


    //Tests
    //implementation 'com.google.firebase:firebase-analytics-ktx'


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