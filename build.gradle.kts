plugins {
    kotlin("jvm") version "2.0.0"
    kotlin("plugin.serialization") version "2.0.0"
    id("org.jetbrains.dokka") version "1.9.20"
    `maven-publish`
}

version = "1.1.2"
group = "dev.fruxz"

repositories {
    mavenCentral()
    maven("https://repo.fruxz.dev/releases")
}

dependencies {

    api("dev.fruxz:ascend:2024.1.2")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")

    implementation("io.ktor:ktor-client-cio:2.3.11")
    implementation("io.ktor:ktor-client-core-jvm:2.3.11")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.11")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.11")

    testImplementation(kotlin("test"))
}

val dokkaHtmlJar by tasks.register<Jar>("dokkaHtmlJar") {
    dependsOn(tasks.dokkaHtml)
    from(tasks.dokkaHtml.flatMap { it.outputDirectory })
    archiveClassifier.set("html-docs")
}

val dokkaJavadocJar by tasks.register<Jar>("dokkaJavadocJar") {
    dependsOn(tasks.dokkaJavadoc)
    from(tasks.dokkaJavadoc.flatMap { it.outputDirectory })
    archiveClassifier.set("javadoc")
}

val sourceJar by tasks.register<Jar>("sourceJar") {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

publishing {
    repositories {
        mavenLocal()
        maven("https://repo.fruxz.dev/releases") {
            name = "fruxz.dev"
            credentials {
                username = project.findProperty("fruxz.dev.user") as? String? ?: System.getenv("FRUXZ_DEV_USER")
                password = project.findProperty("fruxz.dev.secret") as? String? ?: System.getenv("FRUXZ_DEV_SECRET")
            }
        }
    }

    publications.create("Kojang", MavenPublication::class) {
        artifactId = name.lowercase()
        version = version.lowercase()

        artifact(dokkaJavadocJar)
        artifact(dokkaHtmlJar)
        artifact(sourceJar) {
            classifier = "sources"
        }

        from(components["kotlin"])

    }
}

tasks {

    test {
        useJUnitPlatform()
    }

    dokkaHtml.configure {
        outputDirectory.set(layout.projectDirectory.dir("docs"))
    }

}

kotlin {
    jvmToolchain(17)
    explicitApiWarning() // This project is a library, so we want to be as explicit as possible
}

java {
    withJavadocJar()
    withSourcesJar()
}