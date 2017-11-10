package com.commons.network.websock;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;

import org.apache.log4j.Logger;

import com.commons.network.websock.handler.LoginHandler;
import com.commons.util.CharsetUtil;

public class MyWebSocketServerHandler extends
SimpleChannelInboundHandler<Object> {
	private static final Logger logger = Logger
			.getLogger(WebSocketServerHandshaker.class.getName());
	private WebSocketServerHandshaker handshaker;
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// 添加
		Global.group.add(ctx.channel());
		System.out.println("客户端与服务端连接开启");
	}
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		// 移除
		Global.group.remove(ctx.channel());
		System.out.println("客户端与服务端连接关闭");
		PlayerConManager.getInstance().delCon(ctx);
	}
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		if (msg instanceof FullHttpRequest) {
			handleHttpRequest(ctx, ((FullHttpRequest) msg));
		} else if (msg instanceof WebSocketFrame) {
			handlerWebSocketFrame(ctx, (WebSocketFrame) msg);
		}
		
	}
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}
	private void handlerWebSocketFrame(ChannelHandlerContext ctx,
			WebSocketFrame frame) {
		// 判断是否关闭链路的指令
		if (frame instanceof CloseWebSocketFrame) {
			handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame
					.retain());
			PlayerConManager.getInstance().delCon(ctx);
			return ;
		}
		// 判断是否ping消息
		if (frame instanceof PingWebSocketFrame) {
			ctx.channel().write(
					new PongWebSocketFrame(frame.content().retain()));
			return;
		}
		if (frame instanceof BinaryWebSocketFrame) {
			// 二进制消息
			handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame
					.retain());
			PlayerConManager.getInstance().delCon(ctx);
			return ;
		}
		
		if (!LoginHandler.can_login)
			return ;
		
		if (frame instanceof ContinuationWebSocketFrame) {
			String datastr = ((ContinuationWebSocketFrame) frame).text();
			HandlerManager.getInstance().cacheData(ctx, datastr);
			if (!frame.isFinalFragment())
				return ;
			
			datastr = HandlerManager.getInstance().getCacheData(ctx);
			processFullMsg(ctx, datastr);
		}
		
		if (frame instanceof TextWebSocketFrame) {
			String datastr = ((TextWebSocketFrame) frame).text();
			if (!frame.isFinalFragment()) {
				HandlerManager.getInstance().cacheData(ctx, datastr);
				return ;
			}
			
			processFullMsg(ctx, datastr);
		}
		
		/*
		TextWebSocketFrame tws = new TextWebSocketFrame(new Date().toString()
				+ ctx.channel() + "：" + request);
		// 群发
		Global.group.writeAndFlush(tws);
		*/
		// 返回【谁发的发给谁】
		// ctx.channel().writeAndFlush(tws);
	}
	
	private void processFullMsg(final ChannelHandlerContext ctx, final String request) {
		HandlerManager.getInstance().processData(ctx, request);
	}
	@SuppressWarnings("deprecation")
	private void handleHttpRequest(ChannelHandlerContext ctx,
			FullHttpRequest req) {
		if (!req.getDecoderResult().isSuccess()
				|| (!"websocket".equals(req.headers().get("Upgrade")))) {
			sendHttpResponse(ctx, req, new DefaultFullHttpResponse(
					HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
			return;
		}
		WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
				"ws://localhost:7397/websocket", null, false);
		handshaker = wsFactory.newHandshaker(req);
		if (handshaker == null) {
			WebSocketServerHandshakerFactory
			.sendUnsupportedWebSocketVersionResponse(ctx.channel());
		} else {
			handshaker.handshake(ctx.channel(), req);
		}
	}
	private static void sendHttpResponse(ChannelHandlerContext ctx,
			FullHttpRequest req, DefaultFullHttpResponse res) {
		// 返回应答给客户端
		if (res.getStatus().code() != 200) {
			ByteBuf buf = Unpooled.copiedBuffer(res.getStatus().toString(),
					CharsetUtil.defaultEncoding);
			res.content().writeBytes(buf);
			buf.release();
		}
		// 如果是非Keep-Alive，关闭连接
		ChannelFuture f = ctx.channel().writeAndFlush(res);
		if (!isKeepAlive(req) || res.getStatus().code() != 200) {
			f.addListener(ChannelFutureListener.CLOSE);
		}
	}
	private static boolean isKeepAlive(FullHttpRequest req) {
		return false;
	}
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
} 
