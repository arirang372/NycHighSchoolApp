plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.john.nycschools"
    compileSdk = rootProject.findProperty("compileSdkVersion") as Int

    defaultConfig {
        applicationId = "com.john.nycschools"
        minSdk = rootProject.findProperty("minSdkVersion") as Int
        targetSdk = rootProject.findProperty("targetSdkVersion") as Int
        versionCode = rootProject.findProperty("versionCode") as Int
        versionName = "$rootProject.extra[versionName]"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "${rootProject.findProperty("compose_compiler_version")}"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    //ktx extensions
    implementation("androidx.core:core-ktx:${rootProject.findProperty("coreKtxVersion")}")
    implementation("androidx.fragment:fragment-ktx:${rootProject.findProperty("fragmentKtxVersion")}")
    implementation("androidx.activity:activity-ktx:${rootProject.findProperty("activityKtxVersion")}")

    //dagger hilt
    kapt("com.google.dagger:hilt-android-compiler:${rootProject.findProperty("hiltVersion")}")
    implementation("com.google.dagger:hilt-android:${rootProject.findProperty("hiltVersion")}")
    implementation("androidx.hilt:hilt-navigation-compose:${rootProject.findProperty("hiltNavigationComposeVersion")}")


    //google room
    kapt("androidx.room:room-compiler:${rootProject.findProperty("roomVersion")}")
    implementation("androidx.room:room-runtime:${rootProject.findProperty("roomVersion")}")
    implementation("androidx.room:room-ktx:${rootProject.findProperty("roomVersion")}")

    //androidx lifecycle
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:${rootProject.findProperty("lifecycleVersion")}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${rootProject.findProperty("lifecycleVersion")}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${rootProject.findProperty("lifecycleVersion")}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:${rootProject.findProperty("lifecycleVersion")}")


    //retrofit
    implementation("com.google.code.gson:gson:${rootProject.findProperty("gsonVersion")}")
    implementation("com.squareup.okhttp3:logging-interceptor:${rootProject.findProperty("okhttpLoggingVersion")}")
    implementation("com.squareup.retrofit2:converter-gson:${rootProject.findProperty("retrofitVersion")}")
    implementation("com.squareup.retrofit2:retrofit:${rootProject.findProperty("retrofitVersion")}")

    //kotlin coroutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${rootProject.findProperty("coroutinesVersion")}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${rootProject.findProperty("coroutinesVersion")}")

    implementation("androidx.navigation:navigation-compose:${rootProject.findProperty("navigationVersion")}")

    //compose
    implementation("androidx.activity:activity-compose:${rootProject.findProperty("activityComposeVersion")}")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material:${rootProject.findProperty("materialVersion")}")
    implementation("androidx.compose.material:material-icons-extended:${rootProject.findProperty("materialIconExtendedVersion")}")


    testImplementation("org.mockito:mockito-core:5.11.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.2.1")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

}