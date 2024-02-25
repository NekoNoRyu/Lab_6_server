package commands;

import support.*;
import data.StudyGroup;
import support.parser.InvalidDataException;
import support.parser.Parser;

import java.util.Map;
import java.util.Optional;

public class UpdateCommand extends Command {
    @Override
    public void execute(String x) {
        String[] elements = Parser.parseCSVToArray(x);
        if (elements.length != 10) {
            InteractionWithClient.sendMessage(x + ": number of elements for update can only be 10");
        } else {
            try {
                CustomHashMap<Long, StudyGroup> customHashMap = Document.getCustomHashMap();
                long id = Parser.parseId(elements[0]);
                Optional<Map.Entry<Long, StudyGroup>> entryWrap = customHashMap.entrySet().stream()
                        .filter(el -> el.getValue().getId() == id)
                        .findAny();
                if (entryWrap.isEmpty()) {
                    InteractionWithClient.sendMessage(id + ": element with this id is not exists");
                    return;
                }
                Map.Entry<Long, StudyGroup> entry = entryWrap.get();
                Long key = entry.getKey();
                String[] toStudyGroup = new String[9];
                System.arraycopy(elements, 1, toStudyGroup, 0, 9);
                StudyGroup studyGroup = Parser.parseArrayToStudyGroup(toStudyGroup);
                studyGroup.setId(id);
                customHashMap.replace(key, studyGroup);
                Application.requestMenu("dev:save");
            } catch (InvalidDataException e) {
                InteractionWithClient.sendMessage(x + ": problem with parsing");
                InteractionWithClient.sendMessage(e.getMessage());
            } catch (InvalidCommandException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
