package com.amit.multitool.api;

import com.amit.multitool.api.command.Command;
import com.amit.multitool.api.command.CommandFactory;
import com.amit.multitool.api.command.impl.ExitCommand;
import com.amit.multitool.utils.ConsoleInputUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public final class MattermostMultitoolConsoleController implements CommandLineRunner {

    private final CommandFactory commandFactory;

    private boolean isRunning = true;

    @Autowired
    public MattermostMultitoolConsoleController(final CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    @Override
    public void run(final String... args) {
        while (isRunning) {
            this.printMenu();
            final String choice = ConsoleInputUtils.inputValue("Select the function: ");
            this.processCommand(choice);
        }
    }

    private void printMenu() {
        System.out.println("[======= MULTITOOL VIP =======]");
        this.commandFactory.getAllCommands().forEach(command -> System.out.printf("%s - %s%n", command.getName(), command.getDescription()));
    }

    private void processCommand(final String choice) {
        final Command command = commandFactory.getCommand(choice);
        if (command != null) {
            command.execute();
            if (command instanceof ExitCommand) {
                this.isRunning = false;
            }
        } else {
            System.out.println("Invalid command");
        }
    }

}
