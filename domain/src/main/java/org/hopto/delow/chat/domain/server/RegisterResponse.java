package org.hopto.delow.chat.domain.server;

import lombok.Data;
import org.hopto.delow.chat.domain.User;
import org.hopto.delow.chat.domain.server.ServerResponse;

import java.util.List;

@Data
public class RegisterResponse implements ServerResponse {

    private final User user;

    private final String message;

    @Override
    public List<User> getRecipients() {
        return List.of(user);
    }

    @Override
    public Object getBody() {
        return message;
    }

    @Override
    public boolean isBroadcast() {
        return false;
    }

}
