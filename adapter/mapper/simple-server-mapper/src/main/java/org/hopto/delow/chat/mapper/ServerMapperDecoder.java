package org.hopto.delow.chat.mapper;

import lombok.extern.slf4j.Slf4j;
import org.hopto.delow.chat.domain.client.*;
import org.hopto.delow.chat.usecase.port.TransportMapper;

@Slf4j
public class ServerMapperDecoder implements TransportMapper<String, ClientMessage> {

    public ClientMessage process(String input) {
        int delimiterIndex = input.indexOf(" ");

        if (delimiterIndex == -1)
            throw new UnsupportedOperationException("Can't parse command: " + input);

        log.debug("Delimiter index: {}", delimiterIndex);

        String eventTypeString = input.substring(0, delimiterIndex);
        String eventBody = input.substring(delimiterIndex + 1);

        log.debug("type: {}, body: {}", eventTypeString, eventBody);

        ClientMessage output;

        if (MessageType.LOGIN.toString().equals(eventTypeString)) {
            output = LoginMessage.builder()
                    .username(eventBody)
                    .build();
        } else if (MessageType.TEXT.toString().equals(eventTypeString)) {
            output = TextMessage.builder()
                    .message(eventBody)
                    .build();
        } else if (MessageType.COMMAND.toString().equals(eventTypeString)) {
            int commandDelimiterIndex = eventBody.indexOf(" ");
            String command;
            String args;
            if (commandDelimiterIndex > 0) {
                command = eventBody.substring(0, commandDelimiterIndex);
                args = eventBody.substring(commandDelimiterIndex + 1);
            } else {
                command = eventBody;
                args = "";
            }
            output = CommandMessage.builder()
                    .command(command)
                    .args(args)
                    .build();
        } else throw new UnsupportedOperationException(eventTypeString);

        log.debug("Event: {}", output);

        return output;
    }

}
