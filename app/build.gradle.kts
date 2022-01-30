plugins {
    id(BuildPlugins.ANDROID_APPLICATION_PLUGIN)
    id(BuildPlugins.KOTLIN_ANDROID_PLUGIN)
    id(BuildPlugins.KOTLIN_KAPT)
    id(DaggerHilt.DAGGER_HILT)
}

android {
    compileSdk = ProjectProperties.COMPILE_SDK

    defaultConfig {
        applicationId = ProjectProperties.APPLICATION_ID
        minSdk = ProjectProperties.MIN_SDK
        targetSdk = ProjectProperties.TARGET_SDK
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("debug") {
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Lib.Compose.COMPOSE_VERSION
    }

    packagingOptions {
        resources.excludes.add("META-INF/LICENSE.txt")
        resources.excludes.add("META-INF/NOTICE.txt")
        resources.excludes.add("LICENSE.txt")
        resources.excludes.add( "/META-INF/{AL2.0,LGPL2.1}")
    }
}

kapt {
    generateStubs = true
    correctErrorTypes = true
}

dependencies {
    implementation(project(":shared"))
    implementation(project(":navigator"))
    implementation(Lib.Android.KOTLIN_CORE_PLUGIN)
    implementation(Lib.Android.ANDROIDX_APPCOMPAT_PLUGIN)
    implementation(Lib.Android.MATERIAL_PLUGIN)
    implementation(Lib.Wear.PLAY_SERVICE_WEARABLE_PLUGIN)
    implementation(Lib.Wear.WEAR_REMOTE_INTERACTIONS_PLUGIN)
    implementation(WorkManager.WORK_RUNTIME_PLUGIN)
    implementation(Lib.Compose.COMPOSE_UI_PLUGIN)
    implementation(Lib.Compose.COMPOSE_MATERIAL_PLUGIN)
    implementation(Lib.Compose.COMPOSE_UI_TOOLING_PREVIEW_PLUGIN)
    implementation(Lib.Android.ANDROIDX_LIFECYCLE_RUNTIME_PLUGIN)
    implementation(Lib.Android.ANDROIDX_ACTIVITY_COMPOSE_PLUGIN)
    implementation(Lib.Android.ANDROIDX_VIEW_MODEL_PLUGIN)
    implementation(Lib.Navigation.NAVIGATION_COMPOSE_PLUGIN)
    implementation(Lib.Compose.COMPOSE_FOUNDATION_PLUGIN)
    implementation(Lib.Android.ANDROIDX_CONSTRAINTLAYOUT_COMPOSE_PLUGIN)
    implementation(Lib.Android.ANDROIDX_CORE_SPLASH)
    implementation(Lib.Accompanist.ACCOMPANIST_SYSTEM_UI_CONTROLLER_PLUGIN)
    implementation(Lib.Accompanist.ACCOMPANIST_PAGER_PLUGIN)
    implementation(Lib.Accompanist.ACCOMPANIST_SWIPE_REFRESH_PLUGIN)
    implementation(Lib.ExoPlayer.EXOPLAYER_PLUGIN)
    implementation(Lib.Coil.COIL_COMPOSE_PLUGIN)
    implementation(Lib.Coil.COIL_SVG_PLUGIN)
    implementation(Lib.DataStore.DATASTORE_PREFERENCES_PLUGIN)
    implementation(Lib.Android.ANDROIDX_LIFECYCLE_VIEW_MODEL_COMPOSE_PLUGIN)
    implementation(Lib.Skydove.SANDWICH_PLUGIN)
    implementation(Lib.Network.RETROFIT_PLUGIN)
    implementation(Lib.Network.RETROFIT_CONVERTER_GSON_PLUGIN)
    implementation(Lib.Network.OKHTTP_INTERCEPTOR_PLUGIN)
    implementation(Lib.Network.OKHTTP_PLUGIN)
    implementation(DaggerHilt.DAGGER_HILT_NAVIGATION_COMPOSE)
    implementation(DaggerHilt.DAGGER_HILT_NAVIGATION)
    implementation(DaggerHilt.DAGGER_HILT_COMMON)
    implementation(DaggerHilt.DAGGER_HILT_WORK)
    implementation(WorkManager.WORK_MULTIPROCESS_PLUGIN)
    implementation(Lib.Timber.TIMBER_PLUGIN)
    implementation(Lib.Shimmer.SHIMMER_PLUGIN)
    kapt(DaggerHilt.DAGGER_HILT_COMPILER)
    testImplementation(Lib.Android.JUNIT_PLUGIN)
    androidTestImplementation(Lib.Android.JUNIT_EXTENSION_PLUGIN)
    androidTestImplementation(Lib.Android.ESPRESSO_CORE_PLUGIN)
    androidTestImplementation(Lib.Compose.COMPOSE_UI_TESTING_PLUGIN)
    androidTestImplementation(WorkManager.WORK_TESTING)
    debugImplementation(Lib.Compose.COMPOSE_UI_TOOLING_PLUGIN)
}