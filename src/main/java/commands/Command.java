package commands;

import data.StudyGroup;
import lombok.Getter;
import lombok.Setter;
import java.io.*;
import java.util.Arrays;

@Setter
@Getter
public class Command implements Serializable {
    private static final long serialVersionUID = 1L;
    private String type;
    private StudyGroup element;
    private String argument;
    public Command() {}
    public void execute(String x) {}
    public static boolean requiresElement(String commandType) {
        String[] commandsRequiresElement = new String[]{"replace_if_lower", "insert", "update", "remove_lower"}; //перечень команд, требующих элемент в качестве аргумента
        return Arrays.asList(commandsRequiresElement).contains(commandType);
    }
    public static byte[] serialize(Command command) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(command);
            objectOutputStream.close();
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static Command deserialize(byte[] serializedCommand) {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(serializedCommand);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Command command = (Command) objectInputStream.readObject();
            objectInputStream.close();
            return command;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
