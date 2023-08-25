rootProject.name = "csi-transport"

include(
	"netty:csi-transport-netty-common",
	"netty:csi-transport-netty-client",
	"netty:csi-transport-netty-server"
)

dependencyResolutionManagement {
	versionCatalogs {
		create("libs") {
			library("shypl-tool-lang", "org.shypl.tool:tool-lang:1.0.0-SNAPSHOT")
			library("shypl-tool-utils", "org.shypl.tool:tool-utils:1.0.0-SNAPSHOT")
			library("shypl-tool-io", "org.shypl.tool:tool-io:1.0.0-SNAPSHOT")
			library("shypl-tool-logging", "org.shypl.tool:tool-logging:1.0.0-SNAPSHOT")
			
			version("csi-core", "1.0.0-SNAPSHOT")
			library("shypl-csi-core-common", "org.shypl.csi", "csi-core-common").versionRef("csi-core")
			library("shypl-csi-core-client", "org.shypl.csi", "csi-core-client").versionRef("csi-core")
			library("shypl-csi-core-server", "org.shypl.csi", "csi-core-server").versionRef("csi-core")
			
			version("netty", "4.1.97.Final")
			library("netty-transport", "io.netty", "netty-transport").versionRef("netty")
			library("netty-transport-native-epoll", "io.netty", "netty-transport-native-epoll").versionRef("netty")
			library("netty-transport-codec-http", "io.netty", "netty-codec-http").versionRef("netty")
		}
	}
}
