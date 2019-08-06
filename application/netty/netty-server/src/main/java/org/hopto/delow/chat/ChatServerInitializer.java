package org.hopto.delow.chat;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.hopto.delow.chat.config.ManualConfig;

public class ChatServerInitializer extends ChannelInitializer<SocketChannel> {
    private final ManualConfig config;

    public ChatServerInitializer(ManualConfig config) {
        this.config = config;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        pipeline.addLast(new StringDecoder());
        pipeline.addLast(new StringEncoder());

        pipeline.addLast(new ChatProtocolDecoder(config.getProtocolDecoder()));
        pipeline.addLast(new ChatProtocolEncoder(config.getProtocolEncoder()));

        pipeline.addLast(new ChatServerHandler(config.getRegisterUser(), config.getMessageUser(), config.getLogoutUser(), config.getTransportService(), config.getTransportChannelFactory()));
    }

}
