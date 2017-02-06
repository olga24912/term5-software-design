package ru.spbau.mit;

/**
 * Exception throws when expression cann't be parse.
 */
public class ParsingException extends Throwable {
    public ParsingException(String message) {
        super(message);
    }
}
