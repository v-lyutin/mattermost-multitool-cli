package com.amit.multitool.api.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public final class CommandFactory {

    private final Map<String, Command> commands;

    @Autowired
    public CommandFactory(final List<Command> commands) {
        this.commands = commands.stream().collect(Collectors.toMap(Command::getName, Function.identity()));
    }

    public Command getCommand(String choice) {
        return this.commands.get(choice);
    }

    public Collection<Command> getAllCommands() {
        return this.commands.values();
    }

}
