package ua.ithillel.javapro.exception;

public class ExceptionHandler extends RuntimeException {
    public ExceptionHandler(String message) {
        super(message);
    }
    public ExceptionHandler(String message, Throwable cause) {}
}
