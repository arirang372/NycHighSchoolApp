// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    extra.apply {
        set("compileSdkVersion", 34)
        set("compose_compiler_version","1.5.1")

        set("minSdkVersion", 24)
        set("targetSdkVersion", 34)
        set("versionCode", 1)
        set("versionName", "1.0")

        set("coreKtxVersion","1.12.0")
        set("activityComposeVersion","1.8.2")
        set("fragmentKtxVersion","1.6.2")
        set("activityKtxVersion","1.8.2")

        set("materialVersion","1.6.3")
        set("materialIconExtendedVersion","1.6.3")
        set("hiltVersion","2.51")
        set("hiltNavigationComposeVersion","1.2.0")
        set("roomVersion","2.6.1")
        set("pagingVersion","3.2.1")
        set("roomPagingVersion","2.5.2")

        set("gsonVersion","2.10.1")
        set("navigationVersion","2.7.6")
        set("okhttpLoggingVersion","4.11.0")
        set("retrofitVersion","2.9.0")
        set("coroutinesVersion","1.6.4")

        set("lifecycleVersion","2.7.0")
        set("navigationVersion","2.7.7")
    }
}

plugins {
    id("com.android.application") version "8.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.dagger.hilt.android") version "2.51" apply false
}