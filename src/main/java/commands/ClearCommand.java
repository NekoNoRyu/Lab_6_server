package commands;

import support.Application;
import support.CustomHashMap;
import support.Document;
import support.InvalidCommandException;

public class ClearCommand extends Command {
    @Override
    public void execute(String x) {
        Document.setCustomHashMap(new CustomHashMap<>());
        try {
            Application.requestMenu("dev:save");
        } catch (InvalidCommandException e) {
            throw new RuntimeException(e);
        }
    }
}