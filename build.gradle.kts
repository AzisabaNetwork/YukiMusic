import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "1.3.72"
    id("com.github.johnrengelman.shadow") version "5.2.0"
}

group = "io.github.yukileafx"
version = "0.0.4"

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    compileOnly("com.destroystokyo.paper:paper-api:1.12.2-R0.1-SNAPSHOT")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

tasks.withType<ShadowJar> {
    relocate("kotlin", "io.github.yukileafx.yukimusic.libs.kotlin")
    relocate("org.jetbrains.annotations", "io.github.yukileafx.yukimusic.libs.org.jetbrains.annotations")

    minimize()
}
