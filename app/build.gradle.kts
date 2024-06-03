plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.runningapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.runningapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

buildFeatures{
    viewBinding = true
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    //google maps libraries
    implementation (libs.maps.ktx)
    implementation(libs.play.services.maps)
    implementation (libs.play.services.maps.v1802)
    implementation (libs.play.services.location)
    implementation (libs.play.services.maps.v1810)
    implementation (libs.play.services.location.v2100)

    implementation (libs.androidx.room.runtime)
    annotationProcessor (libs.androidx.room.compiler)

    implementation (libs.lottie)
    implementation (libs.material.v150)

    implementation (libs.mpandroidchart)

}