package grepp.coffee.backend.common.exception.member;

import grepp.coffee.backend.common.exception.CoffeeException;
import grepp.coffee.backend.common.exception.ExceptionMessage;

public class MemberException extends CoffeeException {
    public MemberException(ExceptionMessage message) {
        super(message.getText());
    }
}
