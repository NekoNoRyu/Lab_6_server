package support;

import data.StudyGroup;
import lombok.Getter;
import lombok.Setter;

public class Document {
    @Setter
    @Getter
    private static CustomHashMap<Long, StudyGroup> customHashMap = new CustomHashMap<>();
    @Setter
    @Getter
    private static String fileWithCollectionPath = null;
}
