package org.hopto.delow.chat.domain.server;

import lombok.Data;
import org.hopto.delow.chat.domain.User;
import org.hopto.delow.chat.domain.server.ServerResponse;

import java.util.List;

@Data
public class LogoutResponse implements ServerResponse {

    private final User user;

    @Override
    public boolean isBroadcast() {
        return true;
    }

    @Override
    public List<User> getRecipients() {
        return List.of(user);
    }

    @Override
    public Object getBody() {
        if (user.getUsername() != null)
            return "User logged out: " + user.getUsername();
        else
            return "Guest disconnected";
    }

}
