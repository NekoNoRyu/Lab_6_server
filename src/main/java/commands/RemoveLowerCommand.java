package commands;

import support.*;
import data.StudyGroup;
import support.parser.InvalidDataException;
import support.parser.Parser;

import java.util.Map;

public class RemoveLowerCommand extends Command {
    @Override
    public void execute(String x) {
        String[] elements = Parser.parseCSVToArray(x);
        if (elements.length != 9) {
            InteractionWithClient.sendMessage(x + ": number of elements for remove_lower can only be 9");
        } else {
            try {
                StudyGroup studyGroup = Parser.parseArrayToStudyGroup(elements);
                CustomHashMap<Long, StudyGroup> customHashMap = Document.getCustomHashMap();
                CustomHashMap<Long, StudyGroup> customHashMapCopy = new CustomHashMap<>();
                customHashMapCopy.putAll(customHashMap);
                customHashMapCopy.entrySet().stream()
                        .filter(el -> el.getValue().compareTo(studyGroup) < 0)
                        .forEach(el -> customHashMap.remove(el.getKey()));
                Document.setCustomHashMap(customHashMap);
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
