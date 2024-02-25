package support;

import commands.Command;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MenuItem {
    Command command;

    public void clicked(String x) {
        command.execute(x);
    }
}
