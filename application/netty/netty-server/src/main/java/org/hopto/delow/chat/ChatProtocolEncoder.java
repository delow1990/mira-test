package org.hopto.delow.chat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.hopto.delow.chat.domain.server.ServerResponse;
import org.hopto.delow.chat.usecase.port.TransportMapper;

import java.util.List;

public class ChatProtocolEncoder extends MessageToMessageEncoder<ServerResponse> {

    private final TransportMapper<ServerResponse, String> mapper;

    public ChatProtocolEncoder(TransportMapper<ServerResponse, String> mapper) {
        this.mapper = mapper;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, ServerResponse msg, List<Object> out) throws Exception {
        out.add(mapper.process(msg));
    }
}
