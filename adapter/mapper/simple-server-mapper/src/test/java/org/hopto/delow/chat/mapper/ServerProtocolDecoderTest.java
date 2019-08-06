package org.hopto.delow.chat.mapper;

import org.hopto.delow.chat.domain.client.ClientMessage;
import org.hopto.delow.chat.domain.client.CommandMessage;
import org.hopto.delow.chat.domain.client.LoginMessage;
import org.hopto.delow.chat.domain.client.TextMessage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ServerProtocolDecoderTest {

    private static ServerMapperDecoder mapper;

    @BeforeAll
    static void init() {
        mapper = new ServerMapperDecoder();
    }

    @Test
    void shouldDecodeToLogin() {
        String loginString = "LOGIN username";

        ClientMessage clientMessage = mapper.process(loginString);

        assertThat(clientMessage).isInstanceOf(LoginMessage.class);
        LoginMessage loginMessage = (LoginMessage) clientMessage;
        assertThat(loginMessage.getUsername()).isEqualTo("username");
    }

    @Test
    void loginStringShouldThrowUnsupportedOperationException() {
        String eventString = "LOGIN";

        assertThatThrownBy(() -> mapper.process(eventString)).isExactlyInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void shouldDecodeToText() {
        String textString = "TEXT some text that user sent";

        ClientMessage clientMessage = mapper.process(textString);

        assertThat(clientMessage).isInstanceOf(TextMessage.class);
        TextMessage textMessage = (TextMessage) clientMessage;
        assertThat(textMessage.getMessage()).isEqualTo("some text that user sent");
    }

    @Test
    void shouldDecodeToCommand() {
        String commandString = "COMMAND someCommand some args";

        ClientMessage clientMessage = mapper.process(commandString);

        assertThat(clientMessage).isInstanceOf(CommandMessage.class);
        CommandMessage commandMessage = (CommandMessage) clientMessage;
        assertThat(commandMessage.getCommand()).isEqualTo("someCommand");
        assertThat(commandMessage.getArgs()).isEqualTo("some args");

    }



}