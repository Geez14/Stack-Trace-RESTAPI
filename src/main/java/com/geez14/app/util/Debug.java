package com.geez14.app.util;

import org.springframework.security.core.parameters.P;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

final public class Debug {
    private final static String ERROR = "\033[91m"; // red
    private final static String LOG = "\033[92m"; //
    private final static String WARN = "\033[93m"; // yellow
    private final static String INFO = "\033[94m"; // blue
    private final static String OK = "\033[95m"; // pink
    private final static String RESET = "\033[0m"; // reset

    static class Helper {
        private static List<String> messages = new LinkedList<>();

        private static void generate() {
            if (messages.isEmpty())
                return;
            System.out.print(LOG);
            System.out.println("[-----------------------------Special Messages-----------------------------]");
            for (Object message : messages) {
                System.out.print("-> ");
                System.out.println(message);
            }
            System.out.println("[----------------------------------.END-------------------------------------]");
            System.out.println(RESET);
            messages.clear();
        }

        private static void generalMessage(String key, Object message) {
            messages.add(key.concat(":\n\t") + message);
        }
    }

    public static void log(String key, Object message) {
        Helper.generalMessage(key, message);
    }

    public static void flush() {
        Helper.generate();
    }
}
