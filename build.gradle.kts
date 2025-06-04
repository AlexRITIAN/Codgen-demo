import org.gradle.declarative.dsl.schema.FqName.Empty.packageName
import org.gradle.internal.declarativedsl.parsing.main
import org.jooq.impl.DSL.schema
import org.jooq.meta.Databases.database

plugins {
	java
	id("org.springframework.boot") version "3.4.5"
	id("io.spring.dependency-management") version "1.1.7"
	id("io.github.alexritian.codegen-gradle-plugin") version "0.0.2"
}

group = "io.github.alexritian.codegen"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenLocal()
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	jooqGenerator("org.postgresql:postgresql")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

jooq {
//	useContainer = false
	configurations {
		create("main") {
			database {
				schema = "duty"
				includes = ".*"
			}
			output {
				packageName = "io.github.alexritian.codegen"
			}
			forcedTypes {
				timestampToInstant()
			}
		}
	}
}