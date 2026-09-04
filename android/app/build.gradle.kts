plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.solara.browser"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.solara.browser"
        minSdk = 24
        targetSdk = 36
        versionCode = generateVersionCode()
        versionName = getVersionName()

        kapt {
            arguments {
                arg("room.schemaLocation", "$projectDir/schemas")
            }
        }
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
    implementation("androidx.core:core-ktx:1.19.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
    implementation("androidx.activity:activity-compose:1.9.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.7")

    implementation(platform("androidx.compose:compose-bom:2024.11.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")

    implementation("androidx.navigation:navigation-compose:2.8.5")
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")

    implementation("androidx.datastore:datastore-preferences:1.1.1")

    val optimaVersion = project.findProperty("optimaVersion") as? String ?: "0.150.10-dev"
    implementation("org.optima:optima:$optimaVersion")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
}

fun getVersionName(): String {
    System.getenv("APP_VERSION")?.let { return it }
    val versionFile = File("../version.txt")
    return if (versionFile.exists()) {
        versionFile.readLines().firstOrNull()?.trim() ?: "1.10.0"
    } else {
        "1.10.0"
    }
}

fun generateVersionCode(): Int {
    val version = getVersionName()
    val base = version.split("-")[0].split(".").map { it.toIntOrNull() ?: 0 }
    val major = base.getOrElse(0) { 0 }
    val minor = base.getOrElse(1) { 0 }
    val patch = base.getOrElse(2) { 0 }
    val build = base.getOrElse(3) { 0 }

    var code = major * 1000000 + minor * 10000 + patch * 100 + build

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
