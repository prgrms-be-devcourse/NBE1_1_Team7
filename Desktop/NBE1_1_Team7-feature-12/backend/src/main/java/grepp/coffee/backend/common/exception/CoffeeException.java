package grepp.coffee.backend.common.exception;

public abstract class CoffeeException extends RuntimeException {
    public CoffeeException(String message) {
        super(message);
    }
}
