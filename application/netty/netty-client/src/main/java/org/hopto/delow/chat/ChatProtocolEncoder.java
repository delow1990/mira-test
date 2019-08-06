package org.hopto.delow.chat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.hopto.delow.chat.domain.client.ClientMessage;

import java.util.List;

public class ChatProtocolEncoder extends MessageToMessageEncoder<ClientMessage> {

    private final ClientProtocolMapper mapper;

    public ChatProtocolEncoder(ClientProtocolMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, ClientMessage msg, List<Object> out) throws Exception {
        out.add(mapper.encode(msg));
    }
}
