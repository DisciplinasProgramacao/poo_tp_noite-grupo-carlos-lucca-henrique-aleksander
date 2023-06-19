package src.Exceptions;

public class InvalidMidiaException extends RuntimeException {
    public InvalidMidiaException(String message) {
        super(message);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this; // Suppresses the self-suppression
    }
}
