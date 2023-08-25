plugins {
	kotlin("jvm")
	id("java-library")
	id("maven-publish")
}

dependencies {
	implementation(libs.shypl.csi.core.client)
	implementation(libs.shypl.tool.logging)
	api(project(":netty:csi-transport-netty-common"))
}