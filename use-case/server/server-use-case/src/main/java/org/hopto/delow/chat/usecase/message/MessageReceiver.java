package org.hopto.delow.chat.usecase.message;

import org.hopto.delow.chat.domain.client.MessageType;
import org.hopto.delow.chat.domain.client.ClientMessage;
import org.hopto.delow.chat.domain.server.ServerResponse;

public interface MessageReceiver<T extends ClientMessage> {

    MessageType getType();

    ServerResponse process(String userId, T message);

}
