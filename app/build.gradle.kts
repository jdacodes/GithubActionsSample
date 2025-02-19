import com.google.firebase.appdistribution.gradle.firebaseAppDistribution
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.google.firebase.appdistribution)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.detekt)
}

android {
    namespace = "com.jdacodes.githubactionssample"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.jdacodes.githubactionssample"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            group = "QA"
            isMinifyEnabled = false
            firebaseAppDistribution {
                releaseNotesFile = "release_notes.txt"
                testers = "jda.codes@gmail.com"
            }
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            firebaseAppDistribution {
                releaseNotesFile = "release_notes.txt"
                testers = "jda.codes@gmail.com"
            }
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
    tasks.getByPath("preBuild").dependsOn("ktlintFormat")
    ktlint {
        android = true // Enable Android-specific linting rules
        ignoreFailures = false // Fail the build if KtLint finds any issues

        reporters {
            reporter(ReporterType.PLAIN) // Output KtLint results in plain text format
            reporter(ReporterType.HTML) // Output KtLint results in HTML format
            reporter(ReporterType.CHECKSTYLE) // Output KtLint results in Checkstyle/xml format
        }
    }

    tasks.getByPath("preBuild").dependsOn("ktlintFormat")
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
