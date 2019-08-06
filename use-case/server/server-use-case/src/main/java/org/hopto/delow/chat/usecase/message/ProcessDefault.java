package org.hopto.delow.chat.usecase.message;

import org.hopto.delow.chat.domain.client.MessageType;
import org.hopto.delow.chat.domain.User;
import org.hopto.delow.chat.domain.client.ClientMessage;
import org.hopto.delow.chat.domain.server.ErrorResponse;
import org.hopto.delow.chat.domain.server.ServerResponse;
import org.hopto.delow.chat.usecase.port.UserRepository;

public class ProcessDefault implements MessageReceiver<ClientMessage> {

    private final UserRepository<String, User> userRepository;

    public ProcessDefault(UserRepository<String, User> userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public MessageType getType() {
        return MessageType.DEFAULT;
    }

    @Override
    public ServerResponse process(String userId, ClientMessage message) {
        User user = userRepository.findById(userId).orElseThrow();
        return new ErrorResponse(user, "Unsupported operation!");
    }
}
