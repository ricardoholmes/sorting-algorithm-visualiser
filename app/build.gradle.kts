/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java application project to get you started.
 * For more details take a look at the 'Building Java & JVM projects' chapter in the Gradle
 * User Manual available at https://docs.gradle.org/8.1.1/userguide/building_java_projects.html
 */

version = "1.1"

plugins {
    application
    id("org.beryx.jlink") version "3.0.1"
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

application {
    // Define the main class for the application.
    mainClass.set("sortalgorithmvisualiser.App")

    mainModule = "main";
    applicationName = "sorting-algorithm-visualiser"
    project.base.archivesName = "sorting-algorithm-visualiser"
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "sortalgorithmvisualiser.App"
    }
}

// jlink config - for exporting to executable
jlink {
    options = listOf("--strip-debug", "--compress", "2", "--no-header-files", "--no-man-pages")
    launcher {
        name = "Sorting Algorithm Visualiser"
    }
    jpackage {
        if (org.gradle.internal.os.OperatingSystem.current().isWindows()) {
            installerOptions.addAll(listOf("--win-per-user-install", "--win-dir-chooser", "--win-menu", "--win-shortcut"))
            imageOptions.addAll(listOf("--win-console"))
        }
    }
}
