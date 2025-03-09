package ua.ithillel.javapro.exception;

public class DaoExceptionHandler extends Exception {
    public DaoExceptionHandler(String message) {
        super(message);
    }
    public DaoExceptionHandler(String message, Throwable cause) {
        super(message, cause);
    }
}
