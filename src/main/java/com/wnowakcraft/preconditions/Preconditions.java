package com.wnowakcraft.preconditions;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * The class which provides static precondition checks which help to ensure method- and class-level invariants.
 */
public class Preconditions {
    private Preconditions() {
    }

    /**
     * Checks a passed argument reference against being null.
     *
     * @param argument     the actual argument which is checked.
     * @param argumentName the argument's name (used to form the exception message).
     * @param <T>          a type of the passed argument.
     *
     * @return the passed argument reference if it's not null.
     *
     * @throws NullPointerException when the argument reference is null.
     */
    public static <T> T requireNonNull(T argument, String argumentName) {
        return Objects.requireNonNull(argument, message(argumentName, "must not be null"));
    }

    /**
     * Checks a passed argument against being an empty character sequence (length == 0).
     *
     * @param charSequence the actual argument which is checked.
     * @param argumentName the argument's name (used to form the exception message).
     * @param <T>          a concrete type of the character sequence.
     *
     * @return the passed character sequence if it's not empty.
     *
     * @throws NullPointerException     when the passed character sequence reference is null.
     * @throws IllegalArgumentException when the passed character sequence is empty.
     */
    public static <T extends CharSequence> T requireNonEmpty(T charSequence, String argumentName) {
        requireNonNull(charSequence, argumentName);
        requireThat(charSequence.length() > 0, message(argumentName, "must be specified (non empty)"));
        return charSequence;
    }

    /**
     * Checks a passed argument against being an empty collection (size == 0).
     *
     * @param iterable     the actual iterable which is checked.
     * @param argumentName the argument's name (used to form the exception message).
     * @param <I>          an iterable collection type.
     * @param <T>          a concrete element type of the iterable.
     *
     * @return the passed iterable if it's not empty.
     *
     * @throws NullPointerException     when the passed iterable reference is null.
     * @throws IllegalArgumentException when the passed iterable is empty.
     */
    public static <I extends Iterable<T>, T> I requireNonEmpty(I iterable, String argumentName) {
        requireNonNull(iterable, argumentName);
        requireThat(iterable.iterator().hasNext(), message(argumentName, "collection must contain at least one element"));
        return iterable;
    }

    /**
     * Checks a passed condition, representing internal state, whether it's met.
     *
     * @param condition        the condition to check.
     * @param violationMessage a message describing the violation (used to form the exception message).
     *
     * @throws IllegalStateException when the condition is not met.
     */
    public static void requireStateThat(boolean condition, String violationMessage) {
        if (!condition) {
            throw new IllegalStateException(violationMessage);
        }
    }

    /**
     * Checks a passed condition, representing argument invariant, whether it's met.
     *
     * @param condition        the condition to check.
     * @param violationMessage a message describing the violation (used to form the exception message).
     *
     * @throws IllegalStateException when the condition is not met.
     */
    public static void requireThat(boolean condition, String violationMessage) {
        if (!condition) {
            throw new IllegalArgumentException(violationMessage);
        }
    }

    /**
     * Checks a passed condition whether it's met.
     *
     * @param condition                        the condition to check.
     * @param failedConditionExceptionProvider a supplier which provides an exception when the condition is not met.
     *
     * @throws RuntimeException the exception describing the violation.
     *                          The exception class needs to be a subclass of {@link RuntimeException}
     */
    public static void requireThat(boolean condition, Supplier<RuntimeException> failedConditionExceptionProvider) {
        if (!condition) {
            throw failedConditionExceptionProvider.get();
        }
    }

    private static String message(String argumentName, String violationMessage) {
        return argumentName + " " + violationMessage;
    }
}
