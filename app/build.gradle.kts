plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
}

android {
    namespace = "es.iescarrillo.android.ejemplofirebasetodo"
    compileSdk = 34

    defaultConfig {
        applicationId = "es.iescarrillo.android.ejemplofirebasetodo"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    buildToolsVersion = "34.0.0"
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    // TODO: Añadir dependencias Firebase
    // Dependencias Firebase
    // BOM => herramientas generales de Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))
    // Herramientas de analíticas de uso
    implementation("com.google.firebase:firebase-analytics")
    // Módulo Firebase Real Time
    implementation("com.google.firebase:firebase-database")
    // Módulo Firebase Authentication
    implementation("com.google.firebase:firebase-auth")
    // Servicios de Google
    implementation("com.google.android.gms:play-services-auth")
}