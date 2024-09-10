package grepp.coffee.backend.common.exception.order;


import grepp.coffee.backend.common.exception.CoffeeException;
import grepp.coffee.backend.common.exception.ExceptionMessage;

public class OrderException extends CoffeeException {
    public OrderException(ExceptionMessage message) {
        super(message.getText());
    }
}
