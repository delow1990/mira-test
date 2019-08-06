package org.hopto.delow.chat.usecase.message.command;

import org.hopto.delow.chat.domain.client.CommandMessage;
import org.hopto.delow.chat.domain.server.CommandResponse;
import org.hopto.delow.chat.domain.User;

import java.util.List;
import java.util.stream.Collectors;

public class HelpCommandProcessor implements UserCommandProcessor {

    private final List<UserCommandProcessor> processors;

    public HelpCommandProcessor(List<UserCommandProcessor> processors) {
        this.processors = processors;
    }

    @Override
    public String getCommandString() {
        return "help";
    }

    @Override
    public String getShortDescription() {
        return "Shows this message.";
    }

    @Override
    public String getLongDescription() {
        return "Shows description of all commands available on server or detailed description of a passed command";
    }

    @Override
    public CommandResponse process(CommandMessage event, User user) {
        String args = event.getArgs();
        String response;
        if (args.isEmpty()) {
            response = processors.stream()
                    .map(userCommandProcessor -> "/" + userCommandProcessor.getCommandString() + " - " + userCommandProcessor.getShortDescription() + "\n")
                    .collect(Collectors.joining());
            response+= "/" + this.getCommandString() + " - " + this.getShortDescription();
        } else {
            String commandToGetHelpFor;
            if (args.contains(" "))
                commandToGetHelpFor = args.split(" ")[0];
            else
                commandToGetHelpFor = args;
            response = processors.stream()
                    .filter(userCommandProcessor -> userCommandProcessor.getCommandString().equals(commandToGetHelpFor))
                    .findAny()
                    .map(UserCommandProcessor::getLongDescription)
                    .orElseThrow();
        }
        return new CommandResponse(List.of(user), response);
    }
}
