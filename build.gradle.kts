buildscript {
	repositories {
		google()
		mavenCentral()
	}
	dependencies {
		classpath(Dependencies.Kotlin.gradle_plugin)
		classpath(Dependencies.Kotlin.serialization)
		classpath(Dependencies.Android.gradle_build_tools)
	}
}

allprojects {
	repositories {
		google()
		mavenCentral()
		mavenLocal()
		maven {
			url = uri("https://jitpack.io")
		}
	}
}

tasks.register("clean", Delete::class) {
	delete(rootProject.buildDir)
}
