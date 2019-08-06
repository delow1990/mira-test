package org.hopto.delow.chat;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.concurrent.atomic.AtomicBoolean;

public class ChatClientHandlerInitializer extends ChannelInitializer<SocketChannel> {

    private final AtomicBoolean loggedIn;

    public ChatClientHandlerInitializer(AtomicBoolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        pipeline.addLast( new StringDecoder());
        pipeline.addLast( new StringEncoder());

        pipeline.addLast(new ChatClientHandler(loggedIn));
    }
}