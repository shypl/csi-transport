plugins {
	id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "csi-transport"

include(
	"netty:csi-transport-netty-common",
	"netty:csi-transport-netty-client",
	"netty:csi-transport-netty-server"
)