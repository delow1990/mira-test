package org.hopto.delow.chat.transport;

import org.hopto.delow.chat.domain.server.LoginResponse;
import org.hopto.delow.chat.domain.server.ServerResponse;
import org.hopto.delow.chat.domain.server.LogoutResponse;
import org.hopto.delow.chat.domain.User;
import org.hopto.delow.chat.usecase.port.TransportChannel;
import org.hopto.delow.chat.usecase.port.TransportService;

import java.util.concurrent.ConcurrentHashMap;

public class TransportServiceImpl implements TransportService {

    private final ConcurrentHashMap<String, TransportChannel> channels = new ConcurrentHashMap<>();

    @Override
    public void addChannel(TransportChannel channel, User user) {
        channels.put(user.getId(), channel);
    }

    @Override
    public void send(ServerResponse response) {
        if (response.isBroadcast()) {
            channels.values().stream().filter(TransportChannel::isLoggedIn).forEach(channel -> channel.write(response));
        } else {
            if (response instanceof LoginResponse) {
                channels.get(((LoginResponse) response).getUser().getId()).setLoggedIn(true);
            }
            response.getRecipients().forEach(user -> {
                channels.get(user.getId()).write(response);
            });
        }
    }

    @Override
    public void removeChannel(User user) {
        TransportChannel remove = channels.remove(user.getId());
        remove.close();
    }

    @Override
    public void writeAndRemoveChannel(LogoutResponse response) {
        User user = response.getUser();
        TransportChannel removedChannel = channels.remove(user.getId());
        removedChannel.writeAndClose(response);
    }
}
