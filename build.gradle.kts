// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.0.2" apply false
    id("com.android.library") version "8.0.2" apply false
    kotlin("android") version "1.8.22" apply false
    kotlin("jvm") version "1.8.22" apply false
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}

// Configure Java and Kotlin compatibility for all projects
subprojects {
    // Configure Java compatibility
    tasks.withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }

    // Configure Kotlin compatibility
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "17"
        }
    }
}