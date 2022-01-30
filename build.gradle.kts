// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        maven(url = uri("https://jitpack.io"))
    }
    dependencies {
        classpath(BuildPlugins.TOOLS_BUILD_GRADLE)
        classpath(BuildPlugins.KOTLIN_GRADLE_PLUGIN)
        classpath(BuildPlugins.SAFE_ARGS_NAVIGATION_KOTLIN)
        classpath(DaggerHilt.DAGGER_HILT_PLUGIN)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}