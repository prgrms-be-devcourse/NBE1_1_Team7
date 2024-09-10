package grepp.coffee.backend.model.entity.product.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {

    COFFEE("커피"),
    TEA("티"),
    ADE("에이드"),
    SMOOTHIE("스무디")
    ;
    private final String text;
}
