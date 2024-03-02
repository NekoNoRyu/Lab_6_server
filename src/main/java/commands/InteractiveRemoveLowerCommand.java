package commands;

import support.Application;
import support.CustomHashMap;
import data.StudyGroup;
import support.Document;
import support.InvalidCommandException;

import java.util.Map;

public class InteractiveRemoveLowerCommand extends Command {
    @Override
    public void execute(String x) {
        CustomHashMap<Long, StudyGroup> customHashMap = Document.getCustomHashMap();
        CustomHashMap<Long, StudyGroup> customHashMapCopy = new CustomHashMap<>();
        customHashMapCopy.putAll(customHashMap);
        customHashMapCopy.entrySet().stream()
                .filter(el -> el.getValue().compareTo(this.getElement()) < 0)
                .forEach(el -> customHashMap.remove(el.getKey()));
        Document.setCustomHashMap(customHashMap);
        try {
            Application.requestMenu("dev:save");
        } catch (InvalidCommandException e) {
            throw new RuntimeException(e);
        }
    }
}
