# Preconditions v1.0.x

Small library which provides custom precondition checks for your project
to ensure invariants. The library provides simple and consistent api and
error message wording.

## Available preconditions
1. `requireNonNull` - throws `NullPointerException` when a passed argument is `null`.
2. `requireNonEmpty` (String):
    * throws `NullPointerException` when a passed argument is `null`
    * throws `IllegalArgumentException` when a passed argument is an empty String (`length == 0`).
3. `requireNonEmpty` (Iterable):
    * throws `NullPointerException` when a passed argument is `null`
    * throws `IllegalArgumentException` when a passed argument is an empty Iterable (`size == 0`).
4. `requireStateThat` - throw `IllegalStateException` when a specified condition doesn't evaluate to `true`.
5. `requireThat` - throws `IllegalArgumentException` when a specified condition, based on input parameter,
   doesn't evaluate to `true`.
6. `requireThat` (with custom exception provider) - throws an exception provided by the passed provider when
   a specified condition doesn't evaluate to `true`.