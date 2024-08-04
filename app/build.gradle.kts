plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = "com.kerim.neptisgame01"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.kerim.neptisgame01"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "17"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    // Kotlin standard library (latest stable version)
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.0")

    implementation("androidx.recyclerview:recyclerview:1.2.1")

    // AndroidX Core
    implementation("androidx.core:core-ktx:1.13.1")

    // AndroidX AppCompat
    implementation("androidx.appcompat:appcompat:1.7.0")

    // Material Design
    implementation("com.google.android.material:material:1.12.0")

    // ConstraintLayout
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Retrofit for network calls
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    // Retrofit Logging Interceptor
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")


    // Gson for JSON parsing
    implementation("com.google.code.gson:gson:2.10.1")

    // RecyclerView
    implementation("androidx.recyclerview:recyclerview:1.3.2")

    // Jetpack Compose dependencies
    implementation("androidx.compose.ui:ui:1.6.0")
    implementation("androidx.compose.material:material:1.6.0")
    implementation("androidx.compose.ui:ui-tooling-preview:1.6.0")
    implementation(libs.androidx.activity)
    debugImplementation("androidx.compose.ui:ui-tooling:1.6.0")
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation("androidx.compose.material3:material3:1.1.0")

    // Emoji2 dependencies
    implementation("androidx.emoji2:emoji2:1.4.0")
    implementation("androidx.emoji2:emoji2-views-helper:1.4.0")

    // Testing dependencies
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
}
