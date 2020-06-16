plugins {
    //Application plugins
    id(BuildPlugins.Android.application)
    id(BuildPlugins.Kotlin.android)
    id(BuildPlugins.Kotlin.androidExtensions)

    //Script plugins
    id(ScriptPlugins.buildConfigApplication)
}

android {
    compileSdkVersion(AndroidSdk.compile)

    defaultConfig {
        applicationId = appId
        minSdkVersion(AndroidSdk.min)
        targetSdkVersion(AndroidSdk.target)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = TestLibraries.testRunner
    }
}

dependencies {
    api(project(Modules.Core.core))
    implementation(project(Modules.Core.network))
    implementation(project(Modules.Feature.currency))

    implementation(Libraries.kotlinStdLib)
    implementation(Libraries.appCompat)
    implementation(Libraries.ktxCore)
    implementation(Libraries.constraint)

    testImplementation(TestLibraries.junit4)
}
