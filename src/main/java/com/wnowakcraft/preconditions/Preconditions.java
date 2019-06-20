package com.wnowakcraft.preconditions;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Supplier;

public class Preconditions {
    private Preconditions() {
    }

    public static <T> T requireNonNull(T argument, String argumentName) {
        return Objects.requireNonNull(argument, message(argumentName, "== null"));
    }

    public static <T extends CharSequence> T requireNonEmpty(T charSequence, String argumentName) {
        requireNonNull(charSequence, argumentName);
        requireThat(charSequence.length() > 0, message(argumentName, "needs to be specified"));
        return charSequence;
    }

    public static <T> Collection<T> requireNonEmpty(Collection<T> collection, String argumentName) {
        requireNonNull(collection, argumentName);
        requireThat(collection.size() > 0, message(argumentName, "needs to be specified"));
        return collection;
    }

    public static void requireStateThat(boolean condition, String violationMessage) {
        if (!condition) {
            throw new IllegalStateException(violationMessage);
        }
    }

    public static void requireThat(boolean condition, String violationMessage) {
        if (!condition) {
            throw new IllegalArgumentException(violationMessage);
        }
    }

    public static void requireThat(boolean condition, Supplier<RuntimeException> conditionFailedExceptionProvider) {
        if (!condition) {
            throw conditionFailedExceptionProvider.get();
        }
    }

    private static String message(String argumentName, String violationMessage) {
        return argumentName + " " + violationMessage;
    }
}
