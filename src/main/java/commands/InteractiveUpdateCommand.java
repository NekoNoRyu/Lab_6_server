package commands;

import support.*;
import data.StudyGroup;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

public class InteractiveUpdateCommand extends Command {
    @Override
    public void execute(String x) {
        CustomHashMap<Long, StudyGroup> customHashMap = Document.getCustomHashMap();
        long id = Long.parseLong(this.getArgument());
        Optional<Map.Entry<Long, StudyGroup>> entryWrap = customHashMap.entrySet().stream()
                .filter(el -> el.getValue().getId() == id)
                .findAny();
        if (entryWrap.isEmpty()) {
            InteractionWithClient.sendMessage(id + ": element with this id is not exists");
            return;
        }
        Map.Entry<Long, StudyGroup> entry = entryWrap.get();
        Long key = entry.getKey();
        this.getElement().setId(id);
        Date creationDate = new Date();
        this.getElement().setCreationDate(creationDate);
        customHashMap.replace(key, this.getElement());
        try {
            Application.requestMenu("dev:save");
        } catch (InvalidCommandException e) {
            throw new RuntimeException(e);
        }
    }
}
