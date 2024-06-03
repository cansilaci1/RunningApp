// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories{
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.google.services)
        classpath(libs.androidx.navigation.safe.args.gradle.plugin.v277)
        classpath(libs.hilt.android.gradle.plugin)
        classpath(libs.androidx.navigation.safe.args.gradle.plugin)
    }
}

plugins {
    id("com.android.application") version "8.4.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id ("com.android.library") version "8.4.0" apply false
    id("com.google.gms.google-services") version "4.3.15" apply false
}
