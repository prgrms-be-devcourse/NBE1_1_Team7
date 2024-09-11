package grepp.coffee.backend.common.exception.question;

import grepp.coffee.backend.common.exception.CoffeeException;
import grepp.coffee.backend.common.exception.ExceptionMessage;

public class QuestionException extends CoffeeException {
    public QuestionException(ExceptionMessage message) {super(message.getText());}
}
