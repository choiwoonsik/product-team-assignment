import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.2.5"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.spring") version "1.9.23"
    kotlin("plugin.jpa") version "1.9.23"
    kotlin("kapt") version "1.9.23"
    id("org.flywaydb.flyway") version "7.7.3"
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

noArg {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

group = "kcd"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    /**
     * spring
     */
    implementation("org.springframework.boot:spring-boot-starter-batch")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.kafka:spring-kafka")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")

    /**
     * kotlin
     */
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    /**
     * flyway
     */
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-mysql")

    /**
     * gson
     */
    implementation("com.google.code.gson:gson:2.10.1")

    /**
     * querydsl
     */
    implementation("com.querydsl:querydsl-jpa:5.1.0:jakarta")
    implementation("com.querydsl:querydsl-core:5.1.0")
    kapt("com.querydsl:querydsl-apt:5.1.0:jakarta")
    kapt("com.querydsl:querydsl-kotlin-codegen:5.1.0")

    implementation("io.springfox:springfox-swagger-ui:3.0.0")

    kapt("org.springframework.boot:spring-boot-configuration-processor")

    runtimeOnly("com.mysql:mysql-connector-j")

    /**
     * test
     */
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.batch:spring-batch-test")
    testImplementation("org.springframework.kafka:spring-kafka-test")
    testImplementation("io.kotest:kotest-runner-junit5:4.6.3")
    testImplementation("io.kotest:kotest-assertions-core:4.6.3")

    /**
     * mockk
     */
    testImplementation("io.mockk:mockk:1.13.3")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

flyway {
    url = "jdbc:mysql://127.0.0.1:3306/product_team?useUnicode=yes&characterEncoding=UTF-8&serverTimezone=UTC&characterEncoding=UTF-8"
    user = "product"
    password = "product"
    baselineVersion = "1"
    baselineOnMigrate = true
    locations = listOf("filesystem:src/main/resources/db/migration").toTypedArray()
    schemas = listOf("product_team").toTypedArray()
}

task<org.flywaydb.gradle.task.FlywayMigrateTask>("flywayMigrateTestDB") {
    description = "migrate product_team_test"
    url = "jdbc:mysql://127.0.0.1:3306/product_team_test?useUnicode=yes&characterEncoding=UTF-8&serverTimezone=UTC&characterEncoding=UTF-8"
    user = "product"
    password = "product"
    schemas = listOf("product_team_test").toTypedArray()
}

task<org.flywaydb.gradle.task.FlywayCleanTask>("flywayCleanTestDB") {
    description = "clean product_team_test"
    url = "jdbc:mysql://127.0.0.1:3306/product_team_test?useUnicode=yes&characterEncoding=UTF-8&serverTimezone=UTC&characterEncoding=UTF-8"
    user = "product"
    password = "product"
    schemas = listOf("product_team_test").toTypedArray()
}

sourceSets {
    create("integrationTest") {
        kotlin {
            setSrcDirs(setOf("src/integrationTest/kotlin"))
        }
        resources {
            setSrcDirs(setOf("src/integrationTest/resources"))
        }
        compileClasspath += sourceSets["test"].runtimeClasspath
        runtimeClasspath += sourceSets["test"].runtimeClasspath
    }
}

val integrationTest = task<Test>("integrationTest") {
    description = "Runs the integration tests"
    group = "verification"
    testClassesDirs = sourceSets["integrationTest"].output.classesDirs
    classpath = sourceSets["integrationTest"].runtimeClasspath
}

val unitTest = task<Test>("unitTest") {
    description = "Runs the unit tests"
    group = "verification"
    testClassesDirs = sourceSets["test"].output.classesDirs
    classpath = sourceSets["test"].runtimeClasspath
}