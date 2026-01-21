plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias {libs.plugins.detekt }
}

detekt {
    config = files("$rootDir/detekt.yml")
    buildUponDefaultConfig = true
    allRules = false
    baseline = file("$projectDir/config/detekt/baseline.xml")
    reports {
        xml.required.set(true)
        html.required.set(true)
        txt.required.set(true)
    }
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    val compileKotlinDebug = tasks.findByName("compileDebugKotlin") as? org.jetbrains.kotlin.gradle.tasks
    .KotlinCompile
    if (compileKotlinDebug != null) {
        dependsOn(compileKotlinDebug)
        classpath.setFrom(compileKotlinDebug.outputs.files)
        classpath.from(compileKotlinDebug.libraries)
    }
}

dependencies {
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.23.8")
}

android {
    namespace =
        "com.example.democicdapp"
    compileSdk {
        version =
            release(36)
    }

    defaultConfig {
        applicationId =
            "com.example.democicdapp"
        minSdk =
            24
        targetSdk =
            36
        versionCode =
            1
        versionName =
            "1.0"

        testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled =
                false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility =
            JavaVersion.VERSION_11
        targetCompatibility =
            JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget =
            "11"
    }
    buildFeatures {
        compose =
            true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
