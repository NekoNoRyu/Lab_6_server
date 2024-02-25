package data;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class Coordinates implements Serializable {
    private static final long serialVersoinUID = 1L;
    private Double x; //Значение поля должно быть больше -682, Поле не может быть null
    private double y;
}