package org.shypl.csi.transport.netty.client

import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.http.websocketx.WebSocketClientProtocolHandler
import org.shypl.csi.core.frontend.ChannelAcceptor
import org.shypl.csi.transport.netty.common.ChannelDelegate
import org.shypl.csi.transport.netty.common.NettyChannelHandler
import org.shypl.csi.transport.netty.common.WebSocketChannelHandler
import org.shypl.csi.transport.netty.common.WebSocketChannelWriter
import org.shypl.tool.logging.ownLogger
import java.net.SocketException

internal class WebSocketChannelAcceptor(
	private val acceptor: ChannelAcceptor
) : NettyChannelHandler() {
	
	override fun userEventTriggered(ctx: ChannelHandlerContext, evt: Any) {
		when (evt) {
			WebSocketClientProtocolHandler.ClientHandshakeStateEvent.HANDSHAKE_COMPLETE -> {
				val channel = ChannelDelegate(ctx.channel(), WebSocketChannelWriter())
				val handler = acceptor.acceptChannel(channel)
				ctx.pipeline()
					.remove(this)
					.addLast(WebSocketChannelHandler(handler))
			}
			WebSocketClientProtocolHandler.ClientHandshakeStateEvent.HANDSHAKE_TIMEOUT  -> {
				ctx.pipeline().remove(this)
				acceptor.acceptFail()
			}
		}
		
		super.userEventTriggered(ctx, evt)
	}
	
	override fun channelUnregistered(ctx: ChannelHandlerContext) {
		acceptor.acceptFail()
	}
	
	@Suppress("OVERRIDE_DEPRECATION")
	override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
		if (cause !is SocketException) {
			ownLogger.warn("Uncaught exception in netty channel pipeline", cause)
		}
		ctx.close()
	}
}
