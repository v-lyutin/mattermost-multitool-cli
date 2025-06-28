package com.amit.multitool.api.command;

public enum CommandOrder {

    EXIT_COMMAND("0"),
    
    SET_MATTERMOST_ENVIRONMENT_COMMAND("1"),
    
    UPLOAD_CHANNEL_MESSAGES_COMMAND("2"),

    GET_USERS_WITH_INCORRECT_EMAIL_COMMAND("3"),

    SEND_OUT_MESSAGES_COMMAND("4"),

    SEND_OUT_MESSAGES_TO_USERS_WITHOUT_MFA_COMMAND("5"),

    UPLOAD_DIRECT_CHANNEL_MESSAGES_COMMAND("6");

    private final String ordinalNumber;

    CommandOrder(String ordinalNumber) {
        this.ordinalNumber = ordinalNumber;
    }

    public String getOrdinalNumber() {
        return this.ordinalNumber;
    }

}
