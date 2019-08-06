package org.hopto.delow.chat.domain.client;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class LoginMessage implements ClientMessage {

    private String username;

    @Override
    public MessageType getType() {
        return MessageType.LOGIN;
    }

}
