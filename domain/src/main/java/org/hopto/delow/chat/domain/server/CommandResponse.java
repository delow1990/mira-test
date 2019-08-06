package org.hopto.delow.chat.domain.server;

import lombok.Data;
import org.hopto.delow.chat.domain.User;
import org.hopto.delow.chat.domain.server.ServerResponse;

import java.util.List;

@Data
public class CommandResponse implements ServerResponse {

    private final List<User> recipients;

    private final String message;

    @Override
    public boolean isBroadcast() {
        return false;
    }

    @Override
    public List<User> getRecipients() {
        return recipients;
    }

    @Override
    public Object getBody() {
        return message;
    }
}
