package org.hopto.delow.chat.domain.server;

import org.hopto.delow.chat.domain.User;

import java.util.List;

public interface ServerResponse {

    boolean isBroadcast();

    List<User> getRecipients();

    Object getBody();

}
