package support;

import java.time.LocalDateTime;

public class CustomHashMap<K, T> extends java.util.HashMap<K, T> {
    private LocalDateTime initTime;

    public CustomHashMap() {
        initTime = LocalDateTime.now();
    }

    public LocalDateTime getInitTime() {
        return initTime;
    }
}