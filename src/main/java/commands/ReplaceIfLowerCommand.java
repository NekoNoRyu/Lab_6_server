package commands;

import support.*;
import data.StudyGroup;
import support.parser.InvalidDataException;
import support.parser.Parser;

public class ReplaceIfLowerCommand extends Command {
    @Override
    public void execute(String x) {
        String[] elements = Parser.parseCSVToArray(x);
        if (elements.length != 10) {
            InteractionWithClient.sendMessage(x + ": number of elements for replace_if_lower can only be 10");
        } else {
            try {
                CustomHashMap<Long, StudyGroup> customHashMap = Document.getCustomHashMap();
                Long key = Parser.parseKey(elements[0]);
                if (customHashMap.containsKey(key)) {
                    String[] toStudyGroup = new String[9];
                    System.arraycopy(elements, 1, toStudyGroup, 0, 9);
                    StudyGroup studyGroup = Parser.parseArrayToStudyGroup(toStudyGroup);
                    if (customHashMap.get(key).compareTo(studyGroup) > 0) {
                        customHashMap.replace(key, studyGroup);
                        Document.setCustomHashMap(customHashMap);
                        Application.requestMenu("dev:save");
                    }
                } else {
                    InteractionWithClient.sendMessage(key + ": element with this key is not exist");
                }
            } catch (InvalidDataException e) {
                InteractionWithClient.sendMessage(x + ": problem with parsing");
                InteractionWithClient.sendMessage(e.getMessage());
            } catch (InvalidCommandException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
