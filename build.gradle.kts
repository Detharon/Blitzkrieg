buildscript {
    repositories {
        mavenCentral()
    }
}

allprojects {
    version = "1.0"
    extra["appName"] = "Blitzkrieg"
    extra["gdxVersion"] = "1.12.1"

    repositories {
        mavenCentral()
    }
}
