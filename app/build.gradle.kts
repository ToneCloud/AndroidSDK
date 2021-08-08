plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    compileSdkVersion(30)
    buildToolsVersion("30.0.3")

    defaultConfig {
        applicationId("com.tone.cloud")
        minSdkVersion(21)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner("androidx.test.runner.AndroidJUnitRunner")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled =  false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),"proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility =  JavaVersion.VERSION_1_8
        targetCompatibility =  JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("com.google.android.material:material:1.2.1")

    // implementation(project(":androidSdk"))
    implementation(fileTree(mapOf("dir" to "libs","include" to listOf("*.jar"))))

    implementation("org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}")
    implementation("androidx.constraintlayout:constraintlayout:2.0.1")
    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
    testImplementation("org.json:json:20210307")
}