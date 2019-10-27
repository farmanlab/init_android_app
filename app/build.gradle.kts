import BuildSettings.compileSdkVersion
import BuildSettings.minSdkVersion
import BuildSettings.targetSdkVersion
import BuildSettings.versionCode
import BuildSettings.versionName
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
    id("com.android.application")
    id("org.jlleitschuh.gradle.ktlint")
    id("com.google.gms.oss.licenses.plugin")
    id("com.github.ben-manes.versions").version("0.27.0")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
//    id("com.google.gms.google-services")
}

android {
    compileSdkVersion(BuildSettings.compileSdkVersion)
    defaultConfig {
        applicationId = "com.farmanlab.${BuildSettings.appName}"
        minSdkVersion(BuildSettings.minSdkVersion)
        targetSdkVersion(BuildSettings.targetSdkVersion)
        versionCode = BuildSettings.versionCode
        versionName = BuildSettings.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        javaCompileOptions {
            annotationProcessorOptions {
                argument("room.schemaLocation", "$projectDir/schemas")
            }
        }
    }
    sourceSets {
        getByName("main").java.srcDirs("src/main/kotlin")
        getByName("test") {
            java.srcDirs("src/test/kotlin")
            java.srcDirs("$projectDir/src/testShared")
        }
        getByName("androidTest") {
            java.srcDirs("src/androidTest/kotlin")
            java.srcDirs("$projectDir/src/testShared")
        }
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_1_8
        sourceCompatibility = JavaVersion.VERSION_1_8
    }

    lintOptions {
        setXmlOutput(file("build/reports/lint/lint-result.xml"))
        setHtmlOutput(file("build/reports/lint/lint-result.html"))
        isAbortOnError = false
    }

    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".dev"
        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    lintOptions {
        isAbortOnError = false
    }

    testOptions.unitTests.isIncludeAndroidResources = true

    dataBinding {
        isEnabled = true
    }

    androidExtensions {
        isExperimental = true
    }

    packagingOptions {
        exclude("LICENSE")
        exclude("LICENSE.txt")
        exclude("NOTICE")
        exclude("META-INF/DEPENDENCIES")
        exclude("META-INF/LICENSE")
        exclude("META-INF/LICENSE.txt")
        exclude("META-INF/NOTICE")
        exclude("META-INF/NOTICE.txt")
        exclude("META-INF/library_release.kotlin_module")
    }
}

ktlint {
    setVersion(Versions.KtLint)
    android.set(true)
    reporters.set(listOf(ReporterType.CHECKSTYLE))
    ignoreFailures.set(true)
    outputToConsole.set(true)
    filter {
        exclude("**/generated/**")
        include("**/kotlin/**")
    }
}

repositories {
    listOf(
        "aac-extensions",
        "common-extensions",
        "kodein-extensions",
        "async-permissions",
        "coroutine-dialog-fragment",
        "content"
    ).forEach {
        maven("http://farmanlab.github.io/$it/repository")
    }
}

dependencies {
    configurations {
        // To avoid conflict
        androidTestImplementation.configure {
            exclude("com.google.code.findbugs")
        }
    }

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    Dependencies.Implementation.values().forEach {
        implementation(it.value)
    }
    Dependencies.TestImplementation.values().forEach {
        testImplementation(it.value)
    }
    Dependencies.AndroidTestImplementation.values().forEach {
        androidTestImplementation(it.value)
    }
    Dependencies.Kapt.values().forEach {
        kapt(it.value)
    }
    Dependencies.DevImplementation.values().forEach {
        debugImplementation(it.value)
    }
}

tasks.withType < KotlinCompile > {
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf("-Xjsr305=strict")
    }
}

configurations {
    ktlint
}
