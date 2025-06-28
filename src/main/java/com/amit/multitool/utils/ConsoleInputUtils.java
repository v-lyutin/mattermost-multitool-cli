package com.amit.multitool.utils;

import java.util.Scanner;

public final class ConsoleInputUtils {

    private static final Scanner scanner = new Scanner(System.in);

    public static String inputValue(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private ConsoleInputUtils() {
        throw new UnsupportedOperationException();
    }

}
