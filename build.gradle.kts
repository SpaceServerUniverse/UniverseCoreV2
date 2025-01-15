plugins {
    java
    `java-library`
    `maven-publish`
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("org.flywaydb.flyway") version "8.0.0"
}

group = "space.yurisi"
version = "1.0"
description = "UniverseCoreV2"

repositories {
    mavenCentral()
    maven {
        name = "papermc-repo"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
    maven {
        name = "sonatype"
        url = uri("https://oss.sonatype.org/content/groups/public/")
    }
    maven {
        name = "anvil-gui"
        url = uri("https://repo.codemc.io/repository/maven-snapshots/")
    }
    maven {
        name = "InvUI"
        url = uri("https://repo.xenondevs.xyz/releases")
    }
}

dependencies {
    // implementation: Paper 側に伝播する依存関係を追加する (プラグインがサーバー側から扱うライブラリ)
    implementation("org.apache.logging.log4j:log4j-api:2.21.0")
    implementation("org.apache.logging.log4j:log4j-core:2.21.0")
    implementation("net.wesjd:anvilgui:1.10.2-SNAPSHOT")
    implementation("net.dv8tion:JDA:5.1.0")
    implementation("xyz.xenondevs.invui:invui:1.37")
    // api: Paper 側に伝播しない依存関係を追加する
    api("org.hibernate:hibernate-core:6.1.5.Final")
    api("com.mysql:mysql-connector-j:8.0.32")
    api("org.flywaydb:flyway-core:8.0.0")
    api("at.favre.lib:bcrypt:0.10.2")
    // compileOnly: コンパイル時のみに依存するライブラリを追加する
    compileOnly("io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")
    compileOnly("net.luckperms:api:5.4")
}

val targetJavaVersion = 21
java {
    val javaVersion = JavaVersion.toVersion(targetJavaVersion)
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
    if (JavaVersion.current() < javaVersion) {
        toolchain.languageVersion.set(JavaLanguageVersion.of(targetJavaVersion))
    }
}

tasks.withType<JavaCompile>().configureEach {
    if (targetJavaVersion >= 10 || JavaVersion.current().isJava10Compatible) {
        options.release.set(targetJavaVersion)
    }
}

tasks.compileJava {
    options.encoding = "UTF-8"
}
