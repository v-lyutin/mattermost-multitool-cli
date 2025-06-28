package com.amit.multitool.api.command.impl;

import com.amit.multitool.api.command.Command;
import com.amit.multitool.api.command.CommandOrder;
import org.springframework.stereotype.Component;

@Component
public final class ExitCommand implements Command {

    private static final String COMMAND_DESCRIPTION = "Exit application";

    @Override
    public void execute() {
        System.out.println("Bye!");
    }

    @Override
    public String getName() {
        return CommandOrder.EXIT_COMMAND.getOrdinalNumber();
    }

    @Override
    public String getDescription() {
        return COMMAND_DESCRIPTION;
    }

}
