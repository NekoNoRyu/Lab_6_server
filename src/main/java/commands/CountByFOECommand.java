package commands;

import support.CustomHashMap;
import data.FormOfEducation;
import data.StudyGroup;
import support.InteractionWithClient;
import support.parser.InvalidDataException;
import support.parser.Parser;
import support.Document;

import java.util.Map;

public class CountByFOECommand extends Command {
    @Override
    public void execute(String x) {
        try {
            FormOfEducation formOfEducation = Parser.parseFormOfEducation(x);
            CustomHashMap<Long, StudyGroup> customHashMap = Document.getCustomHashMap();
            long i = 0;
            i = customHashMap.entrySet().stream()
                    .filter(el -> el.getValue().getFormOfEducation() == formOfEducation).count();
            InteractionWithClient.sendMessage(String.valueOf(i));
        } catch (InvalidDataException e) {
            InteractionWithClient.sendMessage(x + ": problem with parsing");
            InteractionWithClient.sendMessage(e.getMessage());
        }
    }
}
