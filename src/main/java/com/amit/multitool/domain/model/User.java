package com.amit.multitool.domain.model;

public record User(
        String id,
        String username,
        String email,
        String nickname,
        boolean isMfaActive,
        boolean isServiceAccount,
        boolean isActive) {
}
