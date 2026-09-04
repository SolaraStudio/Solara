pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/SolaraStudio/Optima")
            credentials {
                // Read from environment (CI) or local.properties (local)
                username = System.getenv("GITHUB_ACTOR") ?: localProperty("gpr.user")
                password = System.getenv("GITHUB_TOKEN") ?: localProperty("gpr.key")
            }
        }
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/SolaraStudio/Optima")
            credentials {
                username = System.getenv("GITHUB_ACTOR") ?: localProperty("gpr.user")
                password = System.getenv("GITHUB_TOKEN") ?: localProperty("gpr.key")
            }
        }
    }
}

rootProject.name = "Solara"
include(":app")

fun localProperty(key: String): String? {
    val properties = java.util.Properties()
    val localFile = File(rootDir, "local.properties")
    if (localFile.exists()) {
        properties.load(localFile.inputStream())
        return properties.getProperty(key)
    }
    return null
}
