package org.hopto.delow.chat;

import org.hopto.delow.chat.domain.client.*;

public class ClientProtocolMapper {

    public String encode(ClientMessage message) {
        String response = "";
        switch (message.getType()) {
            case DEFAULT:
                break;
            case LOGIN: {
                LoginMessage loginMessage = (LoginMessage) message;
                response = loginMessage.getType().toString() + " " + loginMessage.getUsername() + "\n";
                break;
            }
            case TEXT:
                TextMessage textMessage = (TextMessage) message;
                response = textMessage.getType() + " " + textMessage.getMessage() + "\n";
                break;
            case COMMAND:
                CommandMessage commandMessage = (CommandMessage) message;
                response = commandMessage.getType() + " " + commandMessage.getCommand() + " " + commandMessage.getArgs() + "\n";
                break;
        }
        return response;
    }

}
