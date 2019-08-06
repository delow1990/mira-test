package org.hopto.delow.chat.domain.client;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class TextMessage implements ClientMessage {

    private String message;

    @Override
    public MessageType getType() {
        return MessageType.TEXT;
    }

}
