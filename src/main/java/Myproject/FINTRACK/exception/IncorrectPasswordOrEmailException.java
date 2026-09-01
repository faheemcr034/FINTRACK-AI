package Myproject.FINTRACK.exception;

public class IncorrectPasswordOrEmailException extends RuntimeException {
    public IncorrectPasswordOrEmailException(String message) {
        super(message);
    }
}
