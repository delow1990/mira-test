package org.hopto.delow.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.hopto.delow.chat.domain.User;
import org.hopto.delow.chat.domain.client.ClientMessage;
import org.hopto.delow.chat.domain.server.ErrorResponse;
import org.hopto.delow.chat.domain.server.LogoutResponse;
import org.hopto.delow.chat.domain.server.RegisterResponse;
import org.hopto.delow.chat.domain.server.ServerResponse;
import org.hopto.delow.chat.usecase.LogoutUser;
import org.hopto.delow.chat.usecase.MessageUser;
import org.hopto.delow.chat.usecase.RegisterUser;
import org.hopto.delow.chat.usecase.port.TransportChannelFactory;
import org.hopto.delow.chat.usecase.port.TransportService;

import java.io.IOException;

@Slf4j
public class ChatServerHandler extends SimpleChannelInboundHandler<ClientMessage> {

    private static final String ATTRIBUTE_ID_NAME = "userId";

    private final RegisterUser registerUser;

    private final MessageUser messageUser;

    private final LogoutUser logoutUser;

    private final TransportService transportService;

    private final TransportChannelFactory<Channel> adapterFactory;

    public ChatServerHandler(RegisterUser registerUser, MessageUser messageUser, LogoutUser logoutUser, TransportService transportService, TransportChannelFactory<Channel> adapterFactory) {
        this.registerUser = registerUser;
        this.messageUser = messageUser;
        this.logoutUser = logoutUser;
        this.transportService = transportService;
        this.adapterFactory = adapterFactory;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        Channel incoming = ctx.channel();
        RegisterResponse registerResponse = registerUser.doRegister();
        User user = registerResponse.getUser();
        setUserIdAttribute(incoming, user.getId());
        transportService.addChannel(adapterFactory.wrap(incoming), user);
        transportService.send(registerResponse);

    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        Channel incoming = ctx.channel();
        String userId = getUserIdAttribute(incoming);
        LogoutResponse response = logoutUser.doLogout(userId);
        transportService.writeAndRemoveChannel(response);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ClientMessage message) {
        Channel incoming = channelHandlerContext.channel();
        String userId = getUserIdAttribute(incoming);
        ServerResponse response = messageUser.processMessage(userId, message);
        transportService.send(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        if (cause instanceof IOException) {
            log.warn(cause.getMessage());
//            Channel incoming = ctx.channel();
//            String userId = getUserIdAttribute(incoming);
//            LogoutResponse response = logoutUser.doLogout(userId);
//            transportService.removeChannel(response.getUser());
            ctx.close();
        } else {
            log.error(cause.getMessage(), cause);
//            ctx.close();
        }
    }

    private void setUserIdAttribute(Channel channel, String userId) {
        channel.attr(AttributeKey.valueOf(ATTRIBUTE_ID_NAME)).set(userId);
    }

    private String getUserIdAttribute(Channel channel) {
        return (String) channel.attr(AttributeKey.valueOf(ATTRIBUTE_ID_NAME)).get();
    }

}
