package org.hopto.delow.chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class Client {

    public static void main(String[] args) throws IOException, InterruptedException {
        new Client("localhost", 8000).run();
    }


    private final String host;
    private final int port;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() throws InterruptedException, IOException {
        EventLoopGroup group = new NioEventLoopGroup();
        AtomicBoolean loggedIn = new AtomicBoolean(false);
        try {
            Bootstrap bootstrap = new io.netty.bootstrap.Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChatClientHandlerInitializer(loggedIn));

            Channel channel = bootstrap.connect(host, port).sync().channel();

            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                String msg = in.readLine();
                if (!loggedIn.get())
                    channel.writeAndFlush("LOGIN " + msg + "\r\n");
                else if (msg.startsWith("/")) {
                    channel.writeAndFlush("COMMAND " + msg.substring(1) + "\r\n");
                } else {
                    channel.writeAndFlush("TEXT " + msg + "\r\n");
                }
            }
        }
        finally {
            group.shutdownGracefully();
        }
    }

}
