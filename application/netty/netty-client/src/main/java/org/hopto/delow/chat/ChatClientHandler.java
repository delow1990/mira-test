package org.hopto.delow.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicBoolean;

public class ChatClientHandler extends SimpleChannelInboundHandler<String> {

    private AtomicBoolean loggedIn;
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    public ChatClientHandler(AtomicBoolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println(s);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String messageFromServer = msg.toString();

        if (messageFromServer.startsWith("LOGIN")) {
            String substring = messageFromServer.substring("LOGIN".length() + 1);
            loggedIn.set(true);
            System.out.println(substring);
        } else {
            System.out.println(messageFromServer);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}