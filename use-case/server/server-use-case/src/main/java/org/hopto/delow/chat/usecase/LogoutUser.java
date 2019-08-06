package org.hopto.delow.chat.usecase;

import org.hopto.delow.chat.domain.server.LogoutResponse;
import org.hopto.delow.chat.domain.User;
import org.hopto.delow.chat.usecase.port.UserRepository;

import java.util.Optional;

public class LogoutUser {

    private final UserRepository<String, User> userRepository;

    private final UserRepository<String, User> guestRepository;

    public LogoutUser(UserRepository<String, User> userRepository, UserRepository<String, User> guestRepository) {
        this.userRepository = userRepository;
        this.guestRepository = guestRepository;
    }

    public LogoutResponse doLogout(String userId) {

        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            userRepository.delete(user);
            return new LogoutResponse(user);
        } else {
            User user = guestRepository.findById(userId).orElseThrow();
            guestRepository.delete(user);
            return new LogoutResponse(user);
        }
    }
}
