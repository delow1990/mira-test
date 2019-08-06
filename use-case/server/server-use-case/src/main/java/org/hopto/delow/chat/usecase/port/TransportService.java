package org.hopto.delow.chat.usecase.port;

import org.hopto.delow.chat.domain.server.ServerResponse;
import org.hopto.delow.chat.domain.server.LogoutResponse;
import org.hopto.delow.chat.domain.User;

public interface TransportService {

    void addChannel(TransportChannel channel, User user);

    void send(ServerResponse message);

    void removeChannel(User user);

    void writeAndRemoveChannel(LogoutResponse response);
}
