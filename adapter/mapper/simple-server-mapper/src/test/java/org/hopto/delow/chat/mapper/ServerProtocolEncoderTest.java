package org.hopto.delow.chat.mapper;

import org.hopto.delow.chat.domain.User;
import org.hopto.delow.chat.domain.server.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ServerProtocolEncoderTest {

    private static ServerMapperEncoder mapper;

    @BeforeAll
    static void init() {
        mapper = new ServerMapperEncoder();
    }

    @Test
    void encodeRegisterResponse() {
        User user = new User(UUID.randomUUID().toString());
        RegisterResponse response = new RegisterResponse(user, "Welcome!");

        String encodedString = mapper.process(response);

        assertThat(encodedString).isEqualTo("[SERVER] - Welcome!\n");
    }

    @Test
    void encodeLoginResponse() {
        User user = new User(UUID.randomUUID().toString());
        user.setUsername("test");

        LoginResponse response = new LoginResponse(user, "Welcome, " + user.getUsername());

        String encodedString = mapper.process(response);

        assertThat(encodedString).isEqualTo("[SERVER] - Welcome, test\n");
    }

    @Test
    void encodeTextResponse() {
        User user = new User(UUID.randomUUID().toString());
        user.setUsername("test");

        TextResponse textResponse = new TextResponse(user, "Some message by user");

        String encodedString = mapper.process(textResponse);
        assertThat(encodedString).isEqualTo("[test] - Some message by user\n");
    }

    @Test
    void encodeCommandResponse() {
        User user = new User(UUID.randomUUID().toString());
        user.setUsername("test");

        CommandResponse commandResponse = new CommandResponse(List.of(user), "Command result");

        String encodedString = mapper.process(commandResponse);
        assertThat(encodedString).isEqualTo("Command result\n");
    }

    @Test
    void encodeLogoutResponse() {
        User user = new User(UUID.randomUUID().toString());
        user.setUsername("test");

        LogoutResponse logoutResponse = new LogoutResponse(user);

        String encodedString = mapper.process(logoutResponse);
        assertThat(encodedString).isEqualTo("[SERVER] - User logged out: test\n");
    }

    @Test
    void encodeErrorResponse() {
        User user = new User(UUID.randomUUID().toString());
        user.setUsername("test");

        ErrorResponse errorResponse = new ErrorResponse(user, "Error!");

        String encodedString = mapper.process(errorResponse);
        assertThat(encodedString).isEqualTo("[SERVER] - Error!\n");
    }


}