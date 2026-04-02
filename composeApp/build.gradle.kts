import org.gradle.declarative.dsl.schema.FqName.Empty.packageName
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)

    //kotlinxSerialization : kotlinversion
    kotlin("plugin.serialization") version "2.1.0"
    //plugin pour créer et injecter dans BuildConfig les clés de local.properties
    id("com.github.gmazzo.buildconfig") version "5.5.1"
    id("app.cash.sqldelight") version "2.1.0"
}

// Read API key from local.properties
val localProperties = Properties() //import java.utils
val localPropertiesFile = rootProject.file("localfake.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(localPropertiesFile.inputStream())
}

//A ajouter à la racine
buildConfig {
    // Définit le nom de la classe générée
    className("BuildConfig")
    // Le package où la classe sera générée
    packageName("com.example.a02_m1_g2_kmp")

    // Récupération sécurisée de la clé
    val photographerApiKey = localProperties.getProperty("photographer.api") ?: ""

    println("photographerApiKey chargée : $photographerApiKey")

    // Crée le champ pour tous les targets (Android, iOS, Desktop)
    buildConfigField("String", "PHOTOGRAPHER_API_KEY", "\"$photographerApiKey\"")
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            //isStatic = true
        }
    }

    jvm()

    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.activity.compose)

            //Client de requêtes spécifique à Android
            implementation("io.ktor:ktor-client-okhttp:3.2.2")

            implementation("io.insert-koin:koin-android:4.1.+")

            implementation("app.cash.sqldelight:android-driver:2.1.0")
        }
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            // (les interfaces en gros)
            implementation("io.ktor:ktor-client-core:3.2.2")
            //Intégration avec la bibliothèque de serialisation, gestion des headers
            implementation("io.ktor:ktor-client-content-negotiation:3.2.2")
            //Serialisation JSON
            implementation("io.ktor:ktor-serialization-kotlinx-json:3.2.2")
            //Pour le logger
            implementation("io.ktor:ktor-client-logging:3.2.2")

            //Coil ImageLoader
            implementation("io.coil-kt.coil3:coil-network-ktor3:3.2.0")
            implementation("io.coil-kt.coil3:coil-compose:3.2.0")

            implementation("org.jetbrains.compose.material:material-icons-extended:1.7.3")

            implementation("org.jetbrains.androidx.navigation:navigation-compose:2.9.+")

            implementation("io.insert-koin:koin-compose:4.1.+")
            implementation("io.insert-koin:koin-compose-viewmodel:4.1.+")
            implementation("io.insert-koin:koin-compose-viewmodel-navigation:4.1.+")

            //Base de données
            implementation("app.cash.sqldelight:runtime:2.1.0")
            implementation("app.cash.sqldelight:coroutines-extensions:2.1.0")
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
            //Client de requêtes spécifique au bureau sur JVM donc même qu'Android
            implementation("io.ktor:ktor-client-okhttp:3.2.2")
            implementation("app.cash.sqldelight:sqlite-driver:2.1.0")
        }
        iosMain.dependencies {
            //Client de requêtes spécifique à iOS
            implementation("io.ktor:ktor-client-darwin:3.2.2")
            implementation("app.cash.sqldelight:native-driver:2.1.0")
        }
    }
}

android {
    namespace = "com.example.a02_m1_g2_kmp"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.example.a02_m1_g2_kmp"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(libs.compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "com.example.a02_m1_g2_kmp.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "Photographer"
            packageVersion = "1.0.0"

            description = "Une application de photographes"
            vendor = "MonEntreprise"

            windows {
                shortcut = true
                menu = true
                // Icône pour l'application et l'installateur
                //iconFile.set(project.file("src/commonMain/composeResources/drawable/my_icone.ico"))
            }
            macOS {
                dockName = "PhotographApp"
            }
            linux {
                shortcut = true
            }
        }
    }
}

//À mettre à la racine. Faire une première synchronisation avant d'ajouter ce bloc, à mettre au niveau d'indentation 0
sqldelight {
    databases {
        create("MyDatabase") { //Nom de la classe qui sera générée pour représenter votre base
            //Où il doit aller chercher les fichiers .sq
            packageName.set("com.example.a02_m1_g2_kmp.db")
        }
    }
    linkSqlite.set(true)
}