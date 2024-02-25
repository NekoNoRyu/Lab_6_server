package commands;

import data.*;
import support.*;

import java.util.Date;
import java.util.Map;

public class InteractiveInsertCommand extends Command {
    @Override
    public void execute(String x) {
        CustomHashMap<Long, StudyGroup> customHashMap = Document.getCustomHashMap();
        Long key = Long.valueOf(this.getArgument());
        if (customHashMap.containsKey(key)) {
            InteractionWithClient.sendMessage("Element with this key already exists");
            return;
        }
        long id = 0;
        for (Map.Entry<Long, StudyGroup> entry : customHashMap.entrySet()) {
            if (id < entry.getValue().getId()) {
                id = entry.getValue().getId();
            }
        }
        id++;
        Date creationDate = new Date();
        this.getElement().setId(id);
        this.getElement().setCreationDate(creationDate);
        customHashMap.put(key, this.getElement());
        Document.setCustomHashMap(customHashMap);
        try {
            Application.requestMenu("dev:save");
        } catch (InvalidCommandException e) {
            throw new RuntimeException(e);
        }
    }
}
