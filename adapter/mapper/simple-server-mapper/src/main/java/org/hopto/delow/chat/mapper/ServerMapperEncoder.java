package org.hopto.delow.chat.mapper;

import org.hopto.delow.chat.domain.server.*;
import org.hopto.delow.chat.usecase.port.TransportMapper;

import java.util.List;

public class ServerMapperEncoder implements TransportMapper<ServerResponse, String> {

    @Override
    public String process(ServerResponse input) {
        String output = "";
        if (input instanceof TextResponse) {
            TextResponse textResponse = (TextResponse) input;
            output = "[" + textResponse.getAuthor().getUsername() + "] - " + textResponse.getMessage() + "\n";
        } else if (input instanceof CommandResponse) {
            CommandResponse commandResponse = (CommandResponse) input;
            output = commandResponse.getMessage() + "\n";
        } else if (input instanceof LoginResponse) {
            LoginResponse logoutResponse = (LoginResponse) input;
            output = "LOGIN [SERVER] - " + logoutResponse.getMessage() + "\n";

            List<TextResponse> history = logoutResponse.getHistory();

            StringBuilder outputBuilder = new StringBuilder(output);
            for (TextResponse textResponse:
                    history) {
                outputBuilder.append(process(textResponse));
            }
            output = outputBuilder.toString();

        } else {
            output = "[SERVER] - " + input.getBody().toString() + "\n";
        }
        return output;
    }
}
