package org.hopto.delow.chat.usecase.message.command;

import org.hopto.delow.chat.domain.client.CommandMessage;
import org.hopto.delow.chat.domain.server.CommandResponse;
import org.hopto.delow.chat.domain.User;

public interface UserCommandProcessor {

    String getCommandString();

    String getShortDescription();

    String getLongDescription();

    CommandResponse process(CommandMessage command, User user);

}
