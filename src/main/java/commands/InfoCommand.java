package commands;

import data.StudyGroup;
import support.CustomHashMap;
import support.Document;
import support.InteractionWithClient;

public class InfoCommand extends Command {
    @Override
    public void execute(String x) {
        CustomHashMap<Long, StudyGroup> customHashMap = Document.getCustomHashMap();
        InteractionWithClient.sendMessage(String.valueOf(customHashMap.getClass()));
        InteractionWithClient.sendMessage("Initialisation time: " + customHashMap.getInitTime());
        InteractionWithClient.sendMessage("Number of elements: " + customHashMap.size());
    }
}
