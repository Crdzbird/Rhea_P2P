plugins {
    id(BuildPlugins.ANDROID_APPLICATION_PLUGIN)
    id(BuildPlugins.KOTLIN_ANDROID_PLUGIN)
}

android {
    compileSdk = ProjectProperties.COMPILE_SDK

    defaultConfig {
        applicationId = ProjectProperties.APPLICATION_ID
        minSdk = ProjectProperties.MIN_SDK
        targetSdk = ProjectProperties.TARGET_SDK
        versionCode = 1
        versionName = "1.0"

    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    packagingOptions {
        resources.excludes.add("META-INF/*")
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(Lib.Android.KOTLIN_CORE_PLUGIN)
    implementation(Lib.Android.KOTLINX_COROUTINES_ANDROID_PLUGIN)
    implementation(Lib.Wear.PLAY_SERVICE_WEARABLE_PLUGIN)
    implementation(Lib.Android.ANDROIDX_PERCENT_LAYOUT_PLUGIN)
    implementation(Lib.Android.ANDROIDX_CONSTRAINTLAYOUT_PLUGIN)
    implementation(Lib.Android.LEGACY_SUPPORT_PLUGIN)
    implementation(Lib.Wear.WEAR_PLUGIN)
    implementation(Lib.Wear.WEAR_REMOTE_INTERACTIONS_PLUGIN)
    implementation(Lib.Wear.WEAR_PHONE_INTERACTIONS_PLUGIN)
}