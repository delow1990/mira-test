package org.hopto.delow.chat.domain.client;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class CommandMessage implements ClientMessage {

    private String command;

    private String args;

    @Override
    public MessageType getType() {
        return MessageType.COMMAND;
    }
}
