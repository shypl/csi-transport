plugins {
	kotlin("jvm")
	id("java-library")
	id("maven-publish")
}

dependencies {
	implementation(libs.shypl.csi.core.backend)
	implementation(libs.shypl.tool.utils)
	implementation(libs.shypl.tool.logging)
	api(project(":netty:csi-transport-netty-common"))
}
