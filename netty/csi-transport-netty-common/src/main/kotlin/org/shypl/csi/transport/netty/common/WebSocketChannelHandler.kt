package org.shypl.csi.transport.netty.common

import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame
import org.shypl.csi.core.ChannelHandler
import org.shypl.tool.logging.ownLogger
import java.net.SocketException

class WebSocketChannelHandler(
	private val handler: ChannelHandler
) : NettyChannelHandler() {
	
	private val inputBuffer = ByteBufInputByteBuffer()
	
	override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
		ctx.alloc()
		if (msg is BinaryWebSocketFrame) {
			try {
				inputBuffer.bindSource(msg.content())
				handler.handleChannelInput(inputBuffer)
			}
			finally {
				inputBuffer.releaseSource()
				msg.release()
			}
		}
		else {
			super.channelRead(ctx, msg)
		}
	}
	
	override fun channelUnregistered(ctx: ChannelHandlerContext) {
		handler.handleChannelClose()
	}
	
	@Suppress("OVERRIDE_DEPRECATION")
	override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
		if (cause !is SocketException) {
			ownLogger.warn("Uncaught exception in netty channel pipeline", cause)
		}
		ctx.close()
	}
}
