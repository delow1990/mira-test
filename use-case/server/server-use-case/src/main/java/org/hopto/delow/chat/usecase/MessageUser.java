package org.hopto.delow.chat.usecase;

import org.hopto.delow.chat.domain.client.MessageType;
import org.hopto.delow.chat.domain.client.ClientMessage;
import org.hopto.delow.chat.domain.server.ServerResponse;
import org.hopto.delow.chat.usecase.message.MessageReceiver;

import java.util.HashMap;
import java.util.List;

public class MessageUser {

    private final HashMap<MessageType, MessageReceiver> map = new HashMap<>();

    public MessageUser(List<MessageReceiver> processors) {
        processors.forEach(messageProcessor -> {
            map.put(messageProcessor.getType(), messageProcessor);
        });
    }

    public ServerResponse processMessage(String userId, ClientMessage clientMessage) {
        MessageReceiver messageProcessor = map.getOrDefault(clientMessage.getType(), map.get(MessageType.DEFAULT));
        return messageProcessor.process(userId, clientMessage);
    }
}
