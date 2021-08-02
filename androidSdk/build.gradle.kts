plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    compileSdkVersion(30)
    buildToolsVersion("30.0.3")

    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner("androidx.test.runner.AndroidJUnitRunner")
        consumerProguardFiles("consumer-rules.pro")
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

    kotlinOptions {
        freeCompilerArgs += "-Xexplicit-api=warning"
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("com.google.android.material:material:1.2.1")
    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
}


tasks.register<Delete>("clearLibsDir") {
    delete("build/libs")
}

tasks.register<Jar>("makeJar") {
    dependsOn("clearLibsDir")
    dependsOn("build")
    archiveBaseName.set("ToneCloud-SDKv${Versions.tone}")
    from("build/intermediates/javac/release/classes/")
    from("build/tmp/kotlin-classes/release/")
    exclude("android/")
    exclude { it.name.startsWith("R$") }
    into("/")
}

tasks.register<Copy>("copyJar") {
    dependsOn("makeJar")
    group = "tone"
    from("build/libs")
    into("build/../libs")
}
