plugins {
	kotlin("jvm")
	id("java-library")
	id("maven-publish")
}

dependencies {
	implementation(libs.shypl.tool.logging)
	implementation(libs.shypl.tool.io)
	implementation(libs.shypl.csi.core.common)
	api(libs.netty.transport)
	api(libs.netty.transport.native.epoll)
	api(libs.netty.transport.codec.http)
}
