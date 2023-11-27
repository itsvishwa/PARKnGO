pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = uri("https://repo.repsy.io/mvn/payhere/payhere-mobilesdk-android/"))

    }
}

rootProject.name = "parkngo"
include(":app")
 