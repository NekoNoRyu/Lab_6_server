package support;

import lombok.Getter;

import java.util.HashMap;

@Getter
public class Menu {
    private HashMap<String, MenuItem> hashMap = new HashMap<>();
    public void add(String commandName, MenuItem menuItem) {
        hashMap.put(commandName, menuItem);
    }
    public void clickMenuItem(String commandName, String x) {
        hashMap.get(commandName).clicked(x);
    }
}