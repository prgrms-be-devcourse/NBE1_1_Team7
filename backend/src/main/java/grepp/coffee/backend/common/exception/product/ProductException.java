package grepp.coffee.backend.common.exception.product;


import grepp.coffee.backend.common.exception.CoffeeException;
import grepp.coffee.backend.common.exception.ExceptionMessage;

public class ProductException extends CoffeeException {
    public ProductException(ExceptionMessage message) {
        super(message.getText());
    }
}
