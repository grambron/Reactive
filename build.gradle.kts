import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
    kotlin("jvm") version "1.4.31"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation("org.slf4j:slf4j-api:1.7.36")
    implementation("org.slf4j:slf4j-simple:1.7.36")

    implementation("io.reactivex.rxjava3:rxjava:3.1.3")

    implementation("org.mongodb:mongodb-driver-rx:1.5.0")


    implementation("io.reactivex:rxnetty-http:0.5.3")
    implementation("io.reactivex:rxnetty-common:0.5.3")
    implementation("io.reactivex:rxnetty-tcp:0.5.3")
    implementation("io.netty:netty-all:4.1.74.Final")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}