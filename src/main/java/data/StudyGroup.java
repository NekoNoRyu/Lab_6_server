package data;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class StudyGroup implements Comparable<StudyGroup>, Serializable {
    private static final long serialVersoinUID = 1L;
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private long studentsCount; //Значение поля должно быть больше 0
    private FormOfEducation formOfEducation; //Поле может быть null
    private Semester semesterEnum; //Поле не может быть null
    private Person groupAdmin; //Поле может быть null

    @Override
    public int compareTo(StudyGroup studyGroup) {
        return Long.compare(this.studentsCount, studyGroup.studentsCount);
    }
}