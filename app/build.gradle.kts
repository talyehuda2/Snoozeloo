plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    alias(libs.plugins.compose.compiler)
    id("kotlin-kapt")
}

android {
    namespace = "com.doublelift.snoozeloov2_nove24project"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.doublelift.snoozeloov2_nove24project"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

configurations.all {
    exclude(group = "com.intellij", module = "annotations")
    exclude(group = "com.android.support", module = "support-compat")

}

dependencies {
    implementation (libs.runtime)
    implementation(libs.androidx.lifecycle.process)
    kapt (libs.compiler.v100)

    // AndroidX Core
    implementation(libs.androidx.core.ktx)

    // Compose dependencies
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.compose.foundation)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.kotlinx.serialization.json)

    //Koin
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)
    //Room
    kapt(libs.room.compiler)
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
//    //Testing
//    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    //Time adn date
    implementation (libs.threetenabp)

    //Splashscreen
    implementation(libs.androidx.core.splashscreen)


}