plugins {
    java
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

val gdxVersion = rootProject.extra["gdxVersion"] as String

dependencies {
    implementation("com.badlogicgames.gdx:gdx:$gdxVersion")
    implementation("com.badlogicgames.gdx:gdx-freetype:$gdxVersion")
}
