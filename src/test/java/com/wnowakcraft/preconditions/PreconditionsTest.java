package com.wnowakcraft.preconditions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.Collections.singleton;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SuppressWarnings("ConstantConditions")
class PreconditionsTest {

    @Nested
    class RequireNonNull {
        @Test
        @DisplayName("throws NullPointerException when argument is null")
        void throwsNullPointerException_whenArgumentIsNull() {
            assertThatExceptionOfType(NullPointerException.class)
                    .isThrownBy(() -> Preconditions.requireNonNull(null, "testArgument"))
                    .withMessage("testArgument must not be null");
        }

        @Test
        @DisplayName("throws no exception when argument is non null")
        void throwsNoExceptionAndReturnsPassedArgument_whenArgumentIsNotNull() {
            var anyNonNullObject = new Object();
            assertThat(Preconditions.requireNonNull(anyNonNullObject, "argumentNameDoesntMatter"))
                    .isEqualTo(anyNonNullObject);
        }
    }

    @Nested
    class RequireNonEmptyString {
        @Test
        @DisplayName("throws IllegalArgumentException when argument is empty string")
        void throwsIllegalArgumentException_whenArgumentIsEmptyString() {
            assertThatExceptionOfType(IllegalArgumentException.class)
                    .isThrownBy(() -> Preconditions.requireNonEmpty("", "testArgument"))
                    .withMessage("testArgument must be specified (non empty)");
        }

        @ParameterizedTest(name = "String of \"{0}\"")
        @DisplayName("throws no exception when argument is:")
        @ValueSource(strings = {" ", "    ", " x ", "x", "\n"})
        void throwsNoExceptionAndReturnsPassedArgument_whenArgumentIsNotNull(String nonEmptyString) {
            assertThat(Preconditions.requireNonNull(nonEmptyString, "argumentNameDoesntMatter"))
                    .isEqualTo(nonEmptyString);
        }
    }

    @Nested
    class RequireNonEmptyCollection {
        @Test
        @DisplayName("throws NullPointerException when argument is null string")
        void throwsNullPointerException_whenArgumentIsNullString() {
            assertThatExceptionOfType(NullPointerException.class)
                    .isThrownBy(() -> Preconditions.requireNonEmpty((Iterable<Void>) null, "testArgument"))
                    .withMessage("testArgument must not be null");
        }

        @Test
        @DisplayName("throws IllegalArgumentException when argument is empty collection")
        void throwsIllegalArgumentException_whenArgumentIsEmptyCollection() {
            assertThatExceptionOfType(IllegalArgumentException.class)
                    .isThrownBy(() -> Preconditions.requireNonEmpty(emptyList(), "testArgument"))
                    .withMessage("testArgument collection must contain at least one element");
        }

        @ParameterizedTest(name = "{1}")
        @DisplayName("throws no exception when argument is: ")
        @ArgumentsSource(NonEmptyCollectionTestCases.class)
        @SuppressWarnings("unused")
        void throwsNoExceptionAndReturnsPassedArgument_whenArgumentIsNotNull(Iterable<Object> nonEmptyCollection, String __) {
            assertThat(Preconditions.requireNonNull(nonEmptyCollection, "argumentNameDoesntMatter"))
                    .isEqualTo(nonEmptyCollection);
        }
    }
    static class NonEmptyCollectionTestCases implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
            var element = new Object();
            var anotherElement = new Object();
            return Stream.of(
                    Arguments.of(singleton(element), "collection containing single element"),
                    Arguments.of(List.of(element, element), "collection containing duplicated elements"),
                    Arguments.of(Set.of(element, anotherElement),"collection containing distinct elements")
            );
        }
    }

    @Nested
    class RequireStateThat {
        @Test
        @DisplayName("throws IllegalStateException when argument is a condition which evaluates to false")
        void throwsIllegalArgumentException_whenArgumentIsFalse() {
            assertThatExceptionOfType(IllegalStateException.class)
                    .isThrownBy(() -> Preconditions.requireStateThat(1 == 2, "test state violation message"))
                    .withMessage("test state violation message");
        }

        @Test
        @DisplayName("throws no exception when argument is a condition which evaluates to true")
        void throwsNoException_whenArgumentIsTrue() {
            Preconditions.requireStateThat( 1 == 1, "test state violation message doesn't matter");
        }
    }

    @Nested
    class RequireThat {
        @Test
        @DisplayName("throws IllegalArgumentException when argument is a condition which evaluates to false")
        void throwsIllegalArgumentException_whenArgumentIsFalse() {
            assertThatExceptionOfType(IllegalArgumentException.class)
                    .isThrownBy(() -> Preconditions.requireThat(1 == 2, "test argument violation message"))
                    .withMessage("test argument violation message");
        }

        @Test
        @DisplayName("throws no exception when argument is a condition which evaluates to true")
        void throwsNoException_whenArgumentIsTrue() {
            Preconditions.requireThat( 1 == 1, "test argument violation doesn't matter");
        }
    }

    @Nested
    class RequireThatWithCustomExceptionProvider {
        @Test
        @DisplayName("throws specified exception when argument is a condition which evaluates to false")
        void throwsSpecifiedException_whenArgumentIsFalse() {
            assertThatExceptionOfType(RuntimeException.class)
                    .isThrownBy(() -> Preconditions.requireThat(1 == 2, () -> new RuntimeException("test exception message")))
                    .withMessage("test exception message");
        }

        @Test
        @DisplayName("throws no exception when argument is a condition which evaluates to true")
        void throwsNoException_whenArgumentIsTrue() {
            Preconditions.requireThat( 1 == 1, () -> new RuntimeException("test exception message doesn't matter"));
        }
    }
}