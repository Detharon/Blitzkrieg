plugins {
    java
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

dependencies {
    implementation(project(":core"))

    val gdxVersion: String by rootProject.extra
    implementation("com.badlogicgames.gdx:gdx-backend-lwjgl:$gdxVersion")
    implementation("com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-desktop")
    implementation("com.badlogicgames.gdx:gdx-freetype-platform:$gdxVersion:natives-desktop")
}

extra["mainClassName"] = "com.dth.blitzkrieg.desktop.DesktopLauncher"
extra["assetsDir"] = File("../core/assets")

tasks.register<JavaExec>("run") {
    dependsOn(tasks.classes)

    mainClass.set(project.property("mainClassName") as String)
    classpath = sourceSets["main"].runtimeClasspath
    standardInput = System.`in`
    workingDir = project.property("assetsDir") as File
    isIgnoreExitValue = true
}

tasks.register<Jar>("dist") {
    dependsOn(tasks.classes)

    val mainSourceSet = sourceSets["main"]

    from(mainSourceSet.output.classesDirs)
    from(mainSourceSet.output.resourcesDir)

    // Unpack runtimeClasspath jars
    from(configurations.runtimeClasspath.get().map { zipTree(it) })

    // Include assets
    from(project.property("assetsDir") as File)

    manifest {
        attributes["Main-Class"] = project.property("mainClassName") as String
    }
}
