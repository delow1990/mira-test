package org.hopto.delow.chat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.extern.slf4j.Slf4j;
import org.hopto.delow.chat.domain.client.ClientMessage;
import org.hopto.delow.chat.usecase.port.TransportMapper;

import java.util.List;

@Slf4j
public class ChatProtocolDecoder extends MessageToMessageDecoder<String>{

    private final TransportMapper<String, ClientMessage> mapper;

    public ChatProtocolDecoder(TransportMapper<String, ClientMessage> mapper) {
        this.mapper = mapper;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
        out.add(mapper.process(msg));
    }

}
