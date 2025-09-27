package br.com.alura.AluraFake.util;

import org.slf4j.Logger;

public class LogUtils {

    public static void info(Logger logger, Object caller, String method, String message, Object... args) {
        String context = formatContext(caller, method);
        logger.info(context + " " + message, args);
    }

    public static void warn(Logger logger, Object caller, String method, String message, Object... args) {
        String context = formatContext(caller, method);
        logger.warn(context + " " + message, args);
    }

    public static void error(Logger logger, Object caller, String method, String message, Object... args) {
        String context = formatContext(caller, method);
        logger.error(context + " " + message, args);
    }

    private static String formatContext(Object caller, String method) {
        String className = caller.getClass().getSimpleName();
        return "[" + className + "][" + method + "]";
    }
}