package dev.enchander.rndevops.jester.playground.backend;

public final class Utility {
    private Utility() {

    }

    public static String getClassName(Object obj) {
        if (obj == null) {
            return "null";
        }
        return obj.getClass().getSimpleName();
    }

    public static String generateErrorId(String prefix) {
        return prefix + "!" + java.util.UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

}
