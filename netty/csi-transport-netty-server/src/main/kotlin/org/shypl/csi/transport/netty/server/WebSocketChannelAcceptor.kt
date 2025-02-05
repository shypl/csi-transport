package org.shypl.csi.transport.netty.server

import io.netty.channel.ChannelHandler.Sharable
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler
import org.shypl.csi.core.server.ChannelAcceptor
import org.shypl.csi.transport.netty.common.ChannelDelegate
import org.shypl.csi.transport.netty.common.NettyChannelHandler
import org.shypl.csi.transport.netty.common.WebSocketChannelHandler
import org.shypl.csi.transport.netty.common.WebSocketChannelWriter
import org.shypl.tool.logging.ownLogger
import java.net.SocketException

@Sharable
internal class WebSocketChannelAcceptor(
	private val acceptor: ChannelAcceptor
) : NettyChannelHandler() {
	
	private val sender = WebSocketChannelWriter()
	
	override fun userEventTriggered(ctx: ChannelHandlerContext, evt: Any) {
		if (evt is WebSocketServerProtocolHandler.HandshakeComplete) {
			val channel = ChannelDelegate(ctx.channel(), sender)
			val handler = acceptor.acceptChannel(channel)
			
			ctx.pipeline()
				.remove(this)
				.addLast(WebSocketChannelHandler(handler))
		}
		
		super.userEventTriggered(ctx, evt)
	}
	
	@Suppress("OVERRIDE_DEPRECATION")
	override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
		if (cause !is SocketException) {
			ownLogger.warn("Uncaught exception in netty channel pipeline", cause)
		}
		ctx.close()
	}
}
