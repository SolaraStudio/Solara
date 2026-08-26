plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jlleitschuh.gradle.ktlint") version "11.6.1"
}

android {
    namespace = "com.solara.browser"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.solara.browser"
        minSdk = 24
        targetSdk = 35

        versionCode = generateVersionCode()
        versionName = getVersionName()
    }

    buildTypes {
        release {
            isMinifyEnabled = true
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
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
    implementation("androidx.activity:activity-compose:1.9.2")

    implementation(platform("androidx.compose:compose-bom:2024.11.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    implementation("androidx.webkit:webkit:1.12.1")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
}

fun getVersionName(): String {
    // 1. Use environment variable if set (CI)
    System.getenv("APP_VERSION")?.let { return it }

    // 2. Read from version.txt
    val versionFile = File("../version.txt")
    return if (versionFile.exists()) {
        val firstLine = versionFile.readLines().firstOrNull() ?: "1.10.0"
        firstLine.split("-")[0].trim()
    } else {
        "1.10.0"
    }
}

fun generateVersionCode(): Int {
    val version = getVersionName()
    // Split version into parts (ignoring suffix like -dev, -beta, etc.)
    val base = version.split("-")[0].split(".").map { it.toIntOrNull() ?: 0 }
    val major = base.getOrElse(0) { 0 }
    val minor = base.getOrElse(1) { 0 }
    val patch = base.getOrElse(2) { 0 }

    // Base code: major * 1,000,000 + minor * 10,000 + patch * 100
    var code = major * 1000000 + minor * 10000 + patch * 100

    // Add offset based on suffix
    val suffix = version.split("-").getOrElse(1) { "release" }
    code += when {
        suffix.startsWith("rc") -> 80
        suffix.startsWith("beta") -> 60
        suffix.startsWith("dev") -> 40
        suffix.startsWith("pr") -> 20
        else -> 0
    }

    return code
}

ktlint {
    android = true
    ignoreFailures = false
    reporters {
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.PLAIN)
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
    }
}
