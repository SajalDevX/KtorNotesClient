plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    kotlin("plugin.serialization") version "1.9.10"
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }
    val coroutinesVersion = "1.6.4"
    val koinVersion = "3.3.2"
    val ktorVersion = "2.2.1"
    val datastoreVersion = "1.1.1"

    sourceSets {
        commonMain.dependencies {
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
            implementation("io.ktor:ktor-client-core:$ktorVersion")
            implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
            api("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")

            implementation("io.ktor:ktor-client-logging-jvm:2.1.2")
            implementation("io.ktor:ktor-client-cio:2.1.2")
            api("io.insert-koin:koin-core:$koinVersion")

            implementation("androidx.datastore:datastore-preferences-core:$datastoreVersion")
            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.0-RC.2")
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        androidMain.dependencies {
            api("io.insert-koin:koin-android:$koinVersion")
            implementation("io.ktor:ktor-client-android:$ktorVersion")

            api("androidx.datastore:datastore-preferences:$datastoreVersion")

        }
    }
}

android {
    namespace = "me.mrSajal.ktornotesclient"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
