package org.hopto.delow.chat.transport;

import io.netty.channel.Channel;
import org.hopto.delow.chat.usecase.port.TransportChannel;

public class NettyTransportAdapter implements TransportChannel {

    private final Channel channel;

    private boolean loggedIn = false;

    public NettyTransportAdapter(Channel channel) {
        this.channel = channel;
    }

    @Override
    public boolean isLoggedIn() {
        return loggedIn;
    }

    @Override
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    @Override
    public void write(Object o) {
        channel.writeAndFlush(o);
    }

    @Override
    public void close() {
        channel.close();
    }

    @Override
    public void writeAndClose(Object body) {
        channel.writeAndFlush(body).addListener(future -> {
            channel.close();
        });
    }
}
