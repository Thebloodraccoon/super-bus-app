package ua.bus.app.exception;

public class PasswordEncryptionException extends Exception {
    public PasswordEncryptionException(String message, Exception e) {
        super(message);
    }
}
