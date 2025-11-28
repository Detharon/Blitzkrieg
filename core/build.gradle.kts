plugins {
    java
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

dependencies {
    val gdxVersion: String by rootProject.extra
    implementation("com.badlogicgames.gdx:gdx:$gdxVersion")
    implementation("com.badlogicgames.gdx:gdx-freetype:$gdxVersion")
}
