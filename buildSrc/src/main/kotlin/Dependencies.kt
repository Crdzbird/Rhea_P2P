object BuildPlugins {
    private const val TOOLS_BUILD = "7.1.0"
    private const val KOTLIN_GRADLE = "1.6.10"
    private const val SAFE_NAVIGATION_ARGS = "2.3.5"

    const val TOOLS_BUILD_GRADLE = "com.android.tools.build:gradle:$TOOLS_BUILD"
    const val KOTLIN_GRADLE_PLUGIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:$KOTLIN_GRADLE"

    const val ANDROID_APPLICATION_PLUGIN = "com.android.application"
    const val ANDROID_APPLICATION_LIBRARY_PLUGIN = "com.android.library"
    const val KOTLIN_ANDROID_PLUGIN = "kotlin-android"
    const val KOTLIN_KAPT = "kotlin-kapt"
    const val SAFE_ARGS_NAVIGATION_KOTLIN = "androidx.navigation:navigation-safe-args-gradle-plugin:$SAFE_NAVIGATION_ARGS"
}

object DaggerHilt {
    private const val HILT = "2.40.5"
    private const val HILT_COMMON = "1.0.0"
    private const val HILT_NAVIGATION = "1.0.0-rc01"

    const val DAGGER_HILT_PLUGIN = "com.google.dagger:hilt-android-gradle-plugin:$HILT"
    const val DAGGER_HILT = "dagger.hilt.android.plugin"
    const val DAGGER_HILT_COMPILER = "com.google.dagger:hilt-compiler:$HILT"
    const val DAGGER_HILT_COMMON = "androidx.hilt:hilt-common:$HILT_COMMON"
    const val DAGGER_HILT_WORK = "androidx.hilt:hilt-work:$HILT_COMMON"
    const val DAGGER_HILT_NAVIGATION = "com.google.dagger:hilt-android:$HILT"
    const val DAGGER_HILT_NAVIGATION_COMPOSE = "androidx.hilt:hilt-navigation-compose:$HILT_NAVIGATION"
}

object WorkManager {
    private const val WORK_MANAGER = "2.7.1"

    const val WORK_RUNTIME_PLUGIN = "androidx.work:work-runtime-ktx:$WORK_MANAGER"
    const val WORK_MULTIPROCESS_PLUGIN = "androidx.work:work-multiprocess:$WORK_MANAGER"
    const val WORK_TESTING = "androidx.work:work-testing:$WORK_MANAGER"
}

object Lib {
    object Android {
        private const val KOTLIN_CORE = "1.7.0"
        private const val ANDROIDX_APPCOMPAT = "1.4.1"
        private const val ANDROIDX_LIFECYCLE = "2.4.0"
        private const val MATERIAL = "1.5.0"
        private const val SPLASH = "1.0.0-beta01"
        private const val CONSTRAINT_COMPOSE = "1.0.0"
        private const val ACTIVITY_COMPOSE = "1.4.0"
        private const val JUNIT = "4.13.2"
        private const val JUNIT_EXTENSION = "1.1.3"
        private const val ESPRESSO = "3.4.0"
        private const val KOTLINX_COROUTINE = "1.6.0"
        private const val PERCENT_LAYOUT = "1.0.0"
        private const val CONSTRAINT_LAYOUT = "2.1.3"
        private const val LEGACY_SUPPORT = "1.0.0"

        const val KOTLIN_CORE_PLUGIN = "androidx.core:core-ktx:$KOTLIN_CORE"
        const val ANDROIDX_APPCOMPAT_PLUGIN = "androidx.appcompat:appcompat:$ANDROIDX_APPCOMPAT"
        const val ANDROIDX_CORE_SPLASH = "androidx.core:core-splashscreen:$SPLASH"
        const val LEGACY_SUPPORT_PLUGIN = "androidx.legacy:legacy-support-v4:$LEGACY_SUPPORT"
        const val MATERIAL_PLUGIN = "com.google.android.material:material:$MATERIAL"
        const val ANDROIDX_PERCENT_LAYOUT_PLUGIN = "androidx.percentlayout:percentlayout:$PERCENT_LAYOUT"
        const val ANDROIDX_LIFECYCLE_RUNTIME_PLUGIN = "androidx.lifecycle:lifecycle-runtime-ktx:$ANDROIDX_LIFECYCLE"
        const val ANDROIDX_VIEW_MODEL_PLUGIN = "androidx.lifecycle:lifecycle-viewmodel-compose:$ANDROIDX_LIFECYCLE"
        const val ANDROIDX_LIFECYCLE_VIEW_MODEL_COMPOSE_PLUGIN = "androidx.lifecycle:lifecycle-viewmodel-compose:$ANDROIDX_LIFECYCLE"
        const val ANDROIDX_CONSTRAINTLAYOUT_PLUGIN = "androidx.constraintlayout:constraintlayout:$CONSTRAINT_LAYOUT"
        const val ANDROIDX_CONSTRAINTLAYOUT_COMPOSE_PLUGIN = "androidx.constraintlayout:constraintlayout-compose:$CONSTRAINT_COMPOSE"
        const val ANDROIDX_ACTIVITY_COMPOSE_PLUGIN = "androidx.activity:activity-compose:$ACTIVITY_COMPOSE"
        const val KOTLINX_COROUTINES_ANDROID_PLUGIN = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$KOTLINX_COROUTINE"
        const val JUNIT_PLUGIN = "junit:junit:$JUNIT"
        const val JUNIT_EXTENSION_PLUGIN = "androidx.test.ext:junit:$JUNIT_EXTENSION"
        const val ESPRESSO_CORE_PLUGIN = "androidx.test.espresso:espresso-core:$ESPRESSO"
    }

    object Navigation {
        private const val NAVIGATION_COMPOSE = "2.4.0-rc01"

        const val NAVIGATION_COMPOSE_PLUGIN = "androidx.navigation:navigation-compose:$NAVIGATION_COMPOSE"
    }

    object DataStore {
        private const val DATASTORE = "1.0.0"

        const val DATASTORE_PREFERENCES_PLUGIN = "androidx.datastore:datastore-preferences:$DATASTORE"
    }

    object ExoPlayer {
        private const val EXOPLAYER = "2.16.1"

        const val EXOPLAYER_PLUGIN = "com.google.android.exoplayer:exoplayer:$EXOPLAYER"
    }

    object Accompanist {
        private const val ACCOMPANIST_PAGER = "0.20.0"
        private const val ACCOMPANIST_SWIPEREFRESH = "0.24.0-alpha"
        private const val ACCOMPANIST_SYSTEM_UI = "0.17.0"

        const val ACCOMPANIST_PAGER_PLUGIN = "com.google.accompanist:accompanist-pager:$ACCOMPANIST_PAGER"
        const val ACCOMPANIST_SWIPE_REFRESH_PLUGIN = "com.google.accompanist:accompanist-swiperefresh:$ACCOMPANIST_SWIPEREFRESH"
        const val ACCOMPANIST_SYSTEM_UI_CONTROLLER_PLUGIN = "com.google.accompanist:accompanist-systemuicontroller:$ACCOMPANIST_SYSTEM_UI"
    }

    object Compose {
        const val COMPOSE_VERSION = "1.2.0-alpha01"

        const val COMPOSE_UI_PLUGIN = "androidx.compose.ui:ui:$COMPOSE_VERSION"
        const val COMPOSE_UI_TOOLING_PLUGIN = "androidx.compose.ui:ui-tooling:$COMPOSE_VERSION"
        const val COMPOSE_MATERIAL_PLUGIN = "androidx.compose.material:material:$COMPOSE_VERSION"
        const val COMPOSE_UI_TOOLING_PREVIEW_PLUGIN = "androidx.compose.ui:ui-tooling-preview:$COMPOSE_VERSION"
        const val COMPOSE_FOUNDATION_PLUGIN = "androidx.compose.foundation:foundation:$COMPOSE_VERSION"
        const val COMPOSE_UI_TESTING_PLUGIN = "androidx.compose.ui:ui-test-junit4:$COMPOSE_VERSION"
    }

    object Wear {
        private const val WEAR_SERVICE = "17.1.0"
        private const val WEAR_REMOTE = "1.0.0"
        private const val WEAR_VERSION = "1.2.0"
        private const val WEAR_PHONE_INTERACTIONS = "1.1.0-alpha02"

        const val PLAY_SERVICE_WEARABLE_PLUGIN = "com.google.android.gms:play-services-wearable:$WEAR_SERVICE"
        const val WEAR_PHONE_INTERACTIONS_PLUGIN = "androidx.wear:wear-phone-interactions:$WEAR_PHONE_INTERACTIONS"
        const val WEAR_PLUGIN = "androidx.wear:wear:$WEAR_VERSION"
        const val WEAR_REMOTE_INTERACTIONS_PLUGIN = "androidx.wear:wear-remote-interactions:$WEAR_REMOTE"
    }

    object Timber {
        private const val TIMBER = "5.0.1"

        const val TIMBER_PLUGIN = "com.jakewharton.timber:timber:$TIMBER"
    }

    object Shimmer {
        private const val SHIMMER = "1.0.2"

        const val SHIMMER_PLUGIN = "com.valentinilk.shimmer:compose-shimmer:$SHIMMER"
    }

    object Coil {
        private const val COIL_COMPOSE = "1.4.0"
        private const val COIL_SVG = "1.3.2"

        const val COIL_COMPOSE_PLUGIN = "io.coil-kt:coil-compose:$COIL_COMPOSE"
        const val COIL_SVG_PLUGIN = "io.coil-kt:coil-svg:$COIL_SVG"
    }

    object Network {
        private const val RETROFIT = "2.9.0"
        private const val OKHTTP = "5.0.0-alpha.3"

        const val RETROFIT_PLUGIN = "com.squareup.retrofit2:retrofit:$RETROFIT"
        const val RETROFIT_CONVERTER_GSON_PLUGIN = "com.squareup.retrofit2:converter-gson:$RETROFIT"
        const val OKHTTP_INTERCEPTOR_PLUGIN = "com.squareup.okhttp3:logging-interceptor:$OKHTTP"
        const val OKHTTP_PLUGIN = "com.squareup.okhttp3:okhttp:$OKHTTP"
    }

    object Skydove {
        private const val SANDWICH = "1.2.3"

        const val SANDWICH_PLUGIN = "com.github.skydoves:sandwich:$SANDWICH"
    }
}