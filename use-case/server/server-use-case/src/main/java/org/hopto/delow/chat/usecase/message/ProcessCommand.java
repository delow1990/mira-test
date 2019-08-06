package org.hopto.delow.chat.usecase.message;

import org.hopto.delow.chat.domain.client.MessageType;
import org.hopto.delow.chat.domain.client.CommandMessage;
import org.hopto.delow.chat.domain.server.CommandResponse;
import org.hopto.delow.chat.domain.User;
import org.hopto.delow.chat.usecase.port.UserRepository;
import org.hopto.delow.chat.usecase.message.command.HelpCommandProcessor;
import org.hopto.delow.chat.usecase.message.command.UserCommandProcessor;

import java.util.ArrayList;
import java.util.List;

public class ProcessCommand implements MessageReceiver<CommandMessage> {

    private final List<UserCommandProcessor> processors;

    private final UserRepository<String, User> userRepository;

    public ProcessCommand(List<UserCommandProcessor> processors, UserRepository<String, User> userRepository) {
        this.userRepository = userRepository;
        HelpCommandProcessor helpProcessor = new HelpCommandProcessor(processors);
        this.processors = new ArrayList<>(processors);
        this.processors.add(helpProcessor);
    }

    @Override
    public MessageType getType() {
        return MessageType.COMMAND;
    }

    @Override
    public CommandResponse process(String userId, CommandMessage event) {
        String command = event.getCommand();
        User user = userRepository.findById(userId).orElseThrow();

        return processors.stream()
                .filter(userCommandProcessor -> userCommandProcessor.getCommandString().equals(command))
                .findFirst()
                .map(userCommandProcessor -> userCommandProcessor.process(event, user))
                .orElseThrow();
    }

}
