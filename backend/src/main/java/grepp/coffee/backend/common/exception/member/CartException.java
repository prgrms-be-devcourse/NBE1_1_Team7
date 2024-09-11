package grepp.coffee.backend.common.exception.member;

import grepp.coffee.backend.common.exception.CoffeeException;
import grepp.coffee.backend.common.exception.ExceptionMessage;

public class CartException extends CoffeeException {
    public CartException(ExceptionMessage message) {
        super(message.getText());
    }
}
