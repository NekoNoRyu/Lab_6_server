package commands;

import support.CustomHashMap;
import data.StudyGroup;
import support.InteractionWithClient;
import support.parser.InvalidDataException;
import support.parser.Parser;
import support.Document;

import java.util.Map;

public class FilterGreaterThanSCCommand extends Command {
    @Override
    public void execute(String x) {
        try {
            long studentsCount = Parser.parseStudentsCount(x);
            CustomHashMap<Long, StudyGroup> customHashMap = Document.getCustomHashMap();
            customHashMap.entrySet().stream()
                    .filter(el -> el.getValue().getStudentsCount() > studentsCount)
                    .forEach(el -> InteractionWithClient.sendMessage(el.toString()));
        } catch (InvalidDataException e) {
            InteractionWithClient.sendMessage(x + ": problem with parsing");
            InteractionWithClient.sendMessage(e.getMessage());
        }
    }
}
