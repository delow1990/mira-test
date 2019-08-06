package org.hopto.delow.chat.domain.server;

import lombok.Getter;
import org.hopto.delow.chat.domain.User;

import java.util.List;


@Getter
public class LoginResponse implements ServerResponse {

    private final User user;

    private final String message;

    private final List<TextResponse> history;

    public LoginResponse(User user, String message) {
        this.user = user;
        this.message = message;
        history = List.of();
    }

    public LoginResponse(User user, String message, List<TextResponse> history) {
        this.user = user;
        this.message = message;
        this.history = history;
    }

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
