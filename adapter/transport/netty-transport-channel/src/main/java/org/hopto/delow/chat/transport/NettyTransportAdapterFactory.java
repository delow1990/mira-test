package org.hopto.delow.chat.transport;

import io.netty.channel.Channel;
import org.hopto.delow.chat.usecase.port.TransportChannel;
import org.hopto.delow.chat.usecase.port.TransportChannelFactory;

public class NettyTransportAdapterFactory implements TransportChannelFactory<Channel> {

    @Override
    public TransportChannel wrap(Channel channel) {
        return new NettyTransportAdapter(channel);
    }
}
