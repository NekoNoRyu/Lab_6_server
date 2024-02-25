package commands;

import support.*;
import data.StudyGroup;
import support.parser.InvalidDataException;
import support.parser.Parser;

public class RemoveKeyCommand extends Command {
    @Override
    public void execute(String x) {
        try {
            Long key = Parser.parseKey(x);
            CustomHashMap<Long, StudyGroup> customHashMap = Document.getCustomHashMap();
            if (customHashMap.containsKey(key)) {
                customHashMap.remove(key);
                try {
                    Application.requestMenu("dev:save");
                } catch (InvalidCommandException e) {
                    throw new RuntimeException(e);
                }
            } else {
                InteractionWithClient.sendMessage(key + ": element with this key is not exist");
            }
        } catch (InvalidDataException e) {
            InteractionWithClient.sendMessage(x + ": problem with parsing");
            InteractionWithClient.sendMessage(e.getMessage());
        }
    }
}
