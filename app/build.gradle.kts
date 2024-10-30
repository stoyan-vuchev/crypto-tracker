import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.application.plugin)
    alias(libs.plugins.kotlin.android.plugin)
    alias(libs.plugins.kotlin.compose.plugin)
    alias(libs.plugins.kotlin.serialization.plugin)
    alias(libs.plugins.ksp.plugin)
    alias(libs.plugins.dagger.hilt.android.plugin)
}

android {

    namespace = "com.stoyanvuchev.cryptotracker"
    compileSdk = 35

    defaultConfig {

        applicationId = "com.stoyanvuchev.cryptotracker"

        minSdk = 27
        targetSdk = 35

        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true

        buildConfigField(
            "String",
            "BASE_URL",
            gradleLocalProperties(rootDir, providers).getProperty("base.url") as String
        )

    }

    buildTypes {

        debug {

            isMinifyEnabled = false
            isShrinkResources = false
            isDebuggable = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

        }

        release {

            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

        }

    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        jvmToolchain(17)
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

}

dependencies {

    // Core Libs Bundle Implementation
    implementation(libs.bundles.core)
    implementation(libs.google.material)

    // Lifecycle Libs Bundle Implementation
    implementation(libs.bundles.lifecycle)

    // Jetpack Compose BOM Bundle Implementation
    implementation(libs.bundles.compose)
    debugImplementation(libs.bundles.composeDebug)
    androidTestImplementation(libs.bundles.composeAndroidTesting)

    // Pagination Libs Bundle Implementation
    implementation(libs.bundles.pagination)

    // Dependency Injection Libs Bundle Implementation
    implementation(libs.bundles.dependencyInjection)
    ksp(libs.bundles.dependencyInjectionKsp)

    // Local Storage Libs Bundle Implementation
    implementation(libs.bundles.localStorage)
    ksp(libs.bundles.localStorageKsp)

    // Serialization Libs Bundle Implementation
    implementation(libs.bundles.serialization)

    // Networking Libs Bundle Implementation
    implementation(libs.bundles.networking)

    // Testing Libs Implementation
    testImplementation(libs.bundles.unitTesting)
    androidTestImplementation(libs.bundles.androidTesting)

    // Other Libs Bundle Implementation
    implementation(libs.bundles.other)

}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}