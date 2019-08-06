package org.hopto.delow.chat.config;

import io.netty.channel.Channel;
import lombok.Getter;
import org.hopto.delow.chat.domain.User;
import org.hopto.delow.chat.domain.client.ClientMessage;
import org.hopto.delow.chat.domain.server.ServerResponse;
import org.hopto.delow.chat.domain.server.TextResponse;
import org.hopto.delow.chat.mapper.ServerMapperDecoder;
import org.hopto.delow.chat.mapper.ServerMapperEncoder;
import org.hopto.delow.chat.repository.InMemoryGuestUserRepository;
import org.hopto.delow.chat.repository.InMemoryMessageRepository;
import org.hopto.delow.chat.repository.InMemoryUserRepositoryImpl;
import org.hopto.delow.chat.transport.NettyTransportAdapterFactory;
import org.hopto.delow.chat.transport.TransportServiceImpl;
import org.hopto.delow.chat.usecase.LogoutUser;
import org.hopto.delow.chat.usecase.MessageUser;
import org.hopto.delow.chat.usecase.RegisterUser;
import org.hopto.delow.chat.usecase.message.ProcessCommand;
import org.hopto.delow.chat.usecase.message.ProcessDefault;
import org.hopto.delow.chat.usecase.message.ProcessLogin;
import org.hopto.delow.chat.usecase.message.ProcessTextMessage;
import org.hopto.delow.chat.usecase.message.command.TestCommandProcessor;
import org.hopto.delow.chat.usecase.port.*;

import java.util.List;

@Getter
public class ManualConfig {

    private final UserRepository<String, User> guestRepository = new InMemoryGuestUserRepository();
    private final UserRepository<String, User> userRepository = new InMemoryUserRepositoryImpl();
    private final MessageRepository<TextResponse> messageRepository = new InMemoryMessageRepository();

    private final RegisterUser registerUser = new RegisterUser(guestRepository);
    private final MessageUser messageUser = new MessageUser(List.of(
            new ProcessLogin(guestRepository, userRepository, messageRepository),
            new ProcessTextMessage(userRepository, messageRepository),
            new ProcessDefault(userRepository),
            new ProcessCommand(List.of(
                    new TestCommandProcessor()
            ), userRepository)));
    private final LogoutUser logoutUser = new LogoutUser(userRepository, guestRepository);

    private final TransportChannelFactory<Channel> transportChannelFactory = new NettyTransportAdapterFactory();

    private final TransportService transportService = new TransportServiceImpl();

    private final TransportMapper<String, ClientMessage> protocolDecoder = new ServerMapperDecoder();
    private final TransportMapper<ServerResponse, String> protocolEncoder = new ServerMapperEncoder();

}
