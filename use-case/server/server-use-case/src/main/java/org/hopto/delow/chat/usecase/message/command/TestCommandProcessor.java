package org.hopto.delow.chat.usecase.message.command;

import org.hopto.delow.chat.domain.client.CommandMessage;
import org.hopto.delow.chat.domain.server.CommandResponse;
import org.hopto.delow.chat.domain.User;

import java.util.List;

public class TestCommandProcessor implements UserCommandProcessor {

    @Override
    public String getCommandString() {
        return "test";
    }

    @Override
    public String getShortDescription() {
        return "Returns message \"TEST\" from server";
    }

    @Override
    public String getLongDescription() {
        return "Returns Message \"TEST\" from server.\n" +
                "The command exists only for plain test of command functionality\n";
    }

    @Override
    public CommandResponse process(CommandMessage command, User user) {
        return new CommandResponse(List.of(user), "TEST");
    }
}
