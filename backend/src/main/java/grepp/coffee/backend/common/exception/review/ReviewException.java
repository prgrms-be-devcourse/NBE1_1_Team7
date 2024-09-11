package grepp.coffee.backend.common.exception.review;

import grepp.coffee.backend.common.exception.CoffeeException;
import grepp.coffee.backend.common.exception.ExceptionMessage;

public class ReviewException extends CoffeeException {
    public ReviewException(ExceptionMessage message) {
        super(message.getText());
    }
}
