plugins {
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.serialization") version "1.8.20"
    `maven-publish`
}

group = "dev.fruxz.kojang"
version = "1.1.1"

repositories {
    mavenCentral()
    maven("https://repo.fruxz.dev/releases")
}

dependencies {

    api("dev.fruxz:ascend:2024.1")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

    implementation("io.ktor:ktor-client-cio:2.3.8")
    implementation("io.ktor:ktor-client-core-jvm:2.3.8")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.8")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.8")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    repositories {
        mavenLocal()
    }

    publications.create("Kojang", MavenPublication::class) {

        from(components["kotlin"])

        artifactId = "kojang"
        version = version.lowercase()

    }
}


kotlin {
    jvmToolchain(17)
    explicitApiWarning() // This project is a library, so we want to be as explicit as possible
}