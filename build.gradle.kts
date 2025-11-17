buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("org.robovm:robovm-gradle-plugin:1.0.0-beta-03")
    }
}

allprojects {
    version = "1.0"
    extra["appName"] = "Blitzkrieg"
    extra["gdxVersion"] = "1.9.9"

    repositories {
        mavenCentral()
    }
}
