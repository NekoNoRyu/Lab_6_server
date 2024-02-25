package commands;

import support.*;
import data.FormOfEducation;
import data.StudyGroup;
import support.parser.InvalidDataException;
import support.parser.Parser;

import java.util.Map;

public class RemoveAnyByFOECommand extends Command {
    @Override
    public void execute(String x) {
        try {
            FormOfEducation formOfEducation = Parser.parseFormOfEducation(x);
            CustomHashMap<Long, StudyGroup> customHashMap = Document.getCustomHashMap();
            customHashMap.entrySet().stream()
                    .filter(el -> el.getValue().getFormOfEducation() == formOfEducation)
                    .forEach(el -> customHashMap.remove(el.getKey()));
            Document.setCustomHashMap(customHashMap);
            try {
                Application.requestMenu("dev:save");
            } catch (InvalidCommandException e) {
                throw new RuntimeException(e);
            }
        } catch (InvalidDataException e) {
            InteractionWithClient.sendMessage(x + ": problem with parsing");
            InteractionWithClient.sendMessage(e.getMessage());
        }
    }
}
