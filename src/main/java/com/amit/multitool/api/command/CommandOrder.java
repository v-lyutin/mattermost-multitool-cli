package com.amit.multitool.api.command;

public enum CommandOrder {

    EXIT_COMMAND("0"),
    
    SET_MATTERMOST_ENVIRONMENT_COMMAND("1"),
    
    UPLOAD_CHANNEL_MESSAGES_COMMAND("2"),

    SEND_OUT_MESSAGES_COMMAND("3"),

    SEND_OUT_MESSAGES_TO_USERS_WITHOUT_MFA_COMMAND("4"),

    UPLOAD_DIRECT_CHANNEL_MESSAGES_COMMAND("5"),

    DEACTIVATE_USER_ACCOUNTS("6"),

    INVITE_USERS_TO_TEAM_BY_EMAIL("7");

    private final String ordinalNumber;

    CommandOrder(String ordinalNumber) {
        this.ordinalNumber = ordinalNumber;
    }

    public String getOrdinalNumber() {
        return this.ordinalNumber;
    }

}
