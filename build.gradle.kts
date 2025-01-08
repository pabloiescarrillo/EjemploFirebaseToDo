// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    // TODO: añadir dependencias de Google Services
    // Dependencia de Google Services
    id("com.google.gms.google-services") version "4.4.2" apply false
}