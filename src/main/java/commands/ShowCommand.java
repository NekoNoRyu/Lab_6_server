package commands;

import data.StudyGroup;
import support.Document;
import support.InteractionWithClient;

import java.util.Map;

public class ShowCommand extends Command {
    @Override
    public void execute(String x) {
        Document.getCustomHashMap().entrySet().forEach(el -> InteractionWithClient.sendMessage(el.toString()));
    }
}
