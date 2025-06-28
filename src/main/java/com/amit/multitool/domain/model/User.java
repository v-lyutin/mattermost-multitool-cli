package com.amit.multitool.domain.model;

public record User(
        String id,
        String username,
        String email,
        String nickname,
        boolean isMfaActive,
        boolean isServiceAccount,
        boolean isActive) {

    public boolean isUsernameCorrect() {
        if (email == null || username == null) {
            return false;
        }
        int atIndex = email.indexOf("@");
        if (atIndex <= 0) {
            return false;
        }
        String emailPrefix = email.substring(0, atIndex);
        final int plusIndex = emailPrefix.indexOf("+");
        if (plusIndex > 0) {
            emailPrefix = emailPrefix.substring(0, plusIndex);
        }
        return username.equals(emailPrefix);
    }

}
