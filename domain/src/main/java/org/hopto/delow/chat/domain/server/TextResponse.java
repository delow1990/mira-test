package org.hopto.delow.chat.domain.server;

import lombok.Data;
import org.hopto.delow.chat.domain.User;
import org.hopto.delow.chat.domain.server.ServerResponse;

import java.util.List;

@Data
public class TextResponse implements ServerResponse {

    private final User author;

    private final String message;

    @Override
    public boolean isBroadcast() {
        return true;
    }

    @Override
    public List<User> getRecipients() {
        return List.of();
    }

    @Override
    public Object getBody() {
        return message;
    }

}