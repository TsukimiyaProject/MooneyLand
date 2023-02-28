import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

plugins {
    kotlin("jvm") version "1.8.10"
    java
    `maven-publish`
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.2"
}

group = "mc.tsukimiya.mooney"
version = "1.0.0"

val mcVersion = "1.19.3"

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
    maven {
        url = uri("https://maven.pkg.github.com/tsukimiyaproject/Lib4B")
        credentials {
            username = project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_ACTOR")
            password = project.findProperty("gpr.key") as String? ?: System.getenv("GITHUB_TOKEN")
        }
    }
    maven {
        url = uri("https://maven.pkg.github.com/tsukimiyaproject/MooneyCore")
        credentials {
            username = project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_ACTOR")
            password = project.findProperty("gpr.key") as String? ?: System.getenv("GITHUB_TOKEN")
        }
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:${mcVersion}-R0.1-SNAPSHOT")
    compileOnly("mc.tsukimiya:lib4b:1.1.1")
    compileOnly("mc.tsukimiya.mooney:mooney-core:1.0.0")
    library(kotlin("stdlib"))
    library("org.jetbrains.exposed:exposed-core:0.41.1")
    library("org.jetbrains.exposed:exposed-jdbc:0.41.1")
    library("org.jetbrains.exposed:exposed-dao:0.41.1")
    library("org.xerial:sqlite-jdbc:3.40.0.0")
    library("com.mysql:mysql-connector-j:8.0.31")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

bukkit {
    name = "MooneyLand"
    version = getVersion().toString()
    description = "土地保護プラグイン"
    author = "deceitya"
    main = "mc.tsukimiya.mooney.land.MooneyLand"
    apiVersion = mcVersion.substring(0, mcVersion.lastIndexOf("."))
    load = BukkitPluginDescription.PluginLoadOrder.POSTWORLD
    depend = listOf("Lib4B", "MooneyCore")

    commands {
        register("land") {
            description = "土地保護コマンド"
            permission = "tsukimiya.mooney.land.land"
            usage = "/land help"
        }
    }

    permissions {
        register("tsukimiya.mooney.land.*") {
            children = listOf(
                "tsukimiya.mooney.land.land",
                "tsukimiya.mooney.land.start",
                "tsukimiya.mooney.land.end",
                "tsukimiya.mooney.land.buy",
                "tsukimiya.mooney.land.sell",
                "tsukimiya.mooney.land.invite",
                "tsukimiya.mooney.land.give",
                "tsukimiya.mooney.land.here",
                "tsukimiya.mooney.land.list",
                "tsukimiya.mooney.land.delete",
                "tsukimiya.mooney.land.help"
            )
        }
        register("tsukimiya.mooney.land.land") {
            default = BukkitPluginDescription.Permission.Default.TRUE
        }
        register("tsukimiya.mooney.land.start") {
            default = BukkitPluginDescription.Permission.Default.TRUE
        }
        register("tsukimiya.mooney.land.end") {
            default = BukkitPluginDescription.Permission.Default.TRUE
        }
        register("tsukimiya.mooney.land.buy") {
            default = BukkitPluginDescription.Permission.Default.TRUE
        }
        register("tsukimiya.mooney.land.sell") {
            default = BukkitPluginDescription.Permission.Default.TRUE
        }
        register("tsukimiya.mooney.land.invite") {
            default = BukkitPluginDescription.Permission.Default.TRUE
        }
        register("tsukimiya.mooney.land.give") {
            default = BukkitPluginDescription.Permission.Default.TRUE
        }
        register("tsukimiya.mooney.land.here") {
            default = BukkitPluginDescription.Permission.Default.TRUE
        }
        register("tsukimiya.mooney.land.list") {
            default = BukkitPluginDescription.Permission.Default.TRUE
        }
        register("tsukimiya.mooney.land.delete") {
            default = BukkitPluginDescription.Permission.Default.OP
        }
        register("tsukimiya.mooney.land.help") {
            default = BukkitPluginDescription.Permission.Default.TRUE
        }
    }
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/tsukimiyaproject/MooneyLand")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_ACTOR")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }
    publications {
        register<MavenPublication>("gpr") {
            from(components["java"])
        }
    }
}
