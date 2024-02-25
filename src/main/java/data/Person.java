package data;


import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class Person implements Serializable {
    private static final long serialVersoinUID = 1L;
    private String name; //Поле не может быть null, Строка не может быть пустой
    private java.time.ZonedDateTime birthday; //Поле не может быть null
    private int weight; //Значение поля должно быть больше 0
}