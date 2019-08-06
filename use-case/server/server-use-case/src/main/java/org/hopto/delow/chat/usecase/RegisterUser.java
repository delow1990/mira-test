package org.hopto.delow.chat.usecase;

import lombok.extern.slf4j.Slf4j;
import org.hopto.delow.chat.domain.exception.UserAlreadyExists;
import org.hopto.delow.chat.domain.server.RegisterResponse;
import org.hopto.delow.chat.domain.User;
import org.hopto.delow.chat.usecase.port.UserRepository;

import java.util.UUID;

@Slf4j
public class RegisterUser {

    private final UserRepository<String, User> guestRepository;

    public RegisterUser(UserRepository<String, User> guestRepository) {
        this.guestRepository = guestRepository;
    }

    public RegisterResponse doRegister() {
        String id = UUID.randomUUID().toString();
        User user = new User(id);

        try {
            guestRepository.add(user);
        } catch (UserAlreadyExists userAlreadyExists) {
            log.error(userAlreadyExists.getMessage(), userAlreadyExists);
        }

        return new RegisterResponse(user, "[SERVER] Welcome to chat! Please enter your nickname");
    }

}
