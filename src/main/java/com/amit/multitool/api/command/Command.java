package com.amit.multitool.api.command;

public interface Command {

    void execute();

    String getName();

    String getDescription();

}
