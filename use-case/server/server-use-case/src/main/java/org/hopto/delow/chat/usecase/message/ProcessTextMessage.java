package org.hopto.delow.chat.usecase.message;

import org.hopto.delow.chat.domain.client.MessageType;
import org.hopto.delow.chat.domain.client.TextMessage;
import org.hopto.delow.chat.domain.server.TextResponse;
import org.hopto.delow.chat.domain.User;
import org.hopto.delow.chat.usecase.port.MessageRepository;
import org.hopto.delow.chat.usecase.port.UserRepository;

public class ProcessTextMessage implements MessageReceiver<TextMessage> {

    private final UserRepository<String, User> userRepository;
    private final MessageRepository<TextResponse> messageRepository;

    public ProcessTextMessage(UserRepository<String, User> userRepository, MessageRepository<TextResponse> messageRepository) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }

    @Override
    public MessageType getType() {
        return MessageType.TEXT;
    }

    @Override
    public TextResponse process(String userId, TextMessage event) {

        User user = userRepository.findById(userId).orElseThrow();

        TextResponse textResponse = new TextResponse(user, event.getMessage());

        messageRepository.add(textResponse);

        return textResponse;
    }
}
