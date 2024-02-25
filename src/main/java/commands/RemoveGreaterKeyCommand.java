package commands;

import support.*;
import data.StudyGroup;
import support.parser.InvalidDataException;
import support.parser.Parser;

import java.util.Map;


public class RemoveGreaterKeyCommand extends Command {
    @Override
    public void execute(String x) {
        try {
            CustomHashMap<Long, StudyGroup> customHashMap = Document.getCustomHashMap();
            Long key = Parser.parseKey(x);
            customHashMap.entrySet().stream()
                    .filter(el -> el.getKey() > key)
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
