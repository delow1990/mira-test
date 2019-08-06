package org.hopto.delow.chat.domain.server;

import lombok.Data;
import org.hopto.delow.chat.domain.User;

import java.util.List;

@Data
public class ErrorResponse implements ServerResponse {

    private final User user;

    private final String errorMessage;

    @Override
    public boolean isBroadcast() {
        return false;
    }

    @Override
    public List<User> getRecipients() {
        return List.of(user);
    }

    @Override
    public Object getBody() {
        return errorMessage;
    }
}
