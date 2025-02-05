package org.shypl.csi.transport.netty.server

import io.netty.channel.EventLoopGroup
import org.shypl.tool.utils.Stoppable

interface ServerEventLoopGroups : Stoppable {
	val acceptorGroup: EventLoopGroup
	val channelsGroup: EventLoopGroup
}
