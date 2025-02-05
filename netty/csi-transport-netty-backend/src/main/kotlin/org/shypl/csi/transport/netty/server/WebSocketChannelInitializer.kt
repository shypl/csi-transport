package org.shypl.csi.transport.netty.server

import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel
import io.netty.handler.codec.http.HttpObjectAggregator
import io.netty.handler.codec.http.HttpServerCodec
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler
import org.shypl.csi.core.backend.ChannelAcceptor

internal class WebSocketChannelInitializer(acceptor: ChannelAcceptor) : ChannelInitializer<SocketChannel>() {
	
	private val channelAcceptor = WebSocketChannelAcceptor(acceptor)
	
	override fun initChannel(ch: SocketChannel) {
		ch.pipeline().addLast(
			HttpServerCodec(),
			HttpObjectAggregator(65536),
			WebSocketServerCompressionHandler(),
			WebSocketServerProtocolHandler("/", null, true),
			channelAcceptor
		)
	}
}