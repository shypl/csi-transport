package org.shypl.csi.transport.netty.common

import io.netty.channel.EventLoopGroup
import io.netty.channel.MultiThreadIoEventLoopGroup
import io.netty.channel.epoll.Epoll
import io.netty.channel.epoll.EpollIoHandler
import io.netty.channel.nio.NioIoHandler
import java.net.InetSocketAddress
import java.net.SocketAddress

fun factoryEventLoopGroup(threads: Int, tryUseEpoll: Boolean): EventLoopGroup {
	return MultiThreadIoEventLoopGroup(
		threads,
		if (tryUseEpoll && Epoll.isAvailable()) EpollIoHandler.newFactory() else NioIoHandler.newFactory()
	)
}


fun String.toSocketAddress(): SocketAddress {
	return InetSocketAddress(
		substringBefore(':'),
		substringAfter(':').toInt()
	)
}