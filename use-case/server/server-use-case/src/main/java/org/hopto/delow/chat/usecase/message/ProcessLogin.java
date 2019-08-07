package org.hopto.delow.chat.usecase.message;

import lombok.extern.slf4j.Slf4j;
import org.hopto.delow.chat.domain.client.MessageType;
import org.hopto.delow.chat.domain.User;
import org.hopto.delow.chat.domain.client.LoginMessage;
import org.hopto.delow.chat.domain.exception.UserAlreadyExists;
import org.hopto.delow.chat.domain.server.ErrorResponse;
import org.hopto.delow.chat.domain.server.LoginResponse;
import org.hopto.delow.chat.domain.server.ServerResponse;
import org.hopto.delow.chat.domain.server.TextResponse;
import org.hopto.delow.chat.usecase.port.MessageRepository;
import org.hopto.delow.chat.usecase.port.UserRepository;

import java.util.List;

@Slf4j
public class ProcessLogin implements MessageReceiver<LoginMessage> {

    private final UserRepository<String, User> guestRepository;

    private final UserRepository<String, User> userRepository;

    private final MessageRepository<TextResponse> messageRepository;

    public ProcessLogin(UserRepository<String, User> guestRepository, UserRepository<String, User> userRepository, MessageRepository<TextResponse> messageRepository) {
        this.guestRepository = guestRepository;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }

    @Override
    public MessageType getType() {
        return MessageType.LOGIN;
    }

    @Override
    public ServerResponse process(String userId, LoginMessage event) {
        log.debug("ProcessLogin event");

        User user = guestRepository.findById(userId).orElseThrow();

        user.setUsername(event.getUsername());

        try {
            userRepository.add(user);

        } catch (UserAlreadyExists userAlreadyExists) {
            return new ErrorResponse(user, userAlreadyExists.getMessage());
        }
        guestRepository.delete(user);

        List<TextResponse> textMessages = messageRepository.findAll();

        return new LoginResponse(user, "Welcome, " + user.getUsername(), textMessages);

    }
}
