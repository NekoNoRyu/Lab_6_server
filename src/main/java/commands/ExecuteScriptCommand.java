package commands;

import com.opencsv.RFC4180Parser;
import org.apache.commons.lang3.ArrayUtils;
import support.Application;
import support.InteractionWithClient;
import support.InvalidCommandException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ExecuteScriptCommand extends Command {
    @Override
    public void execute(String x) {
        ArrayList<String> commands = new ArrayList<>();
        HashSet<String> paths = new HashSet<>();
        paths.add(x);
        try {
            File file = new File(x);
            Scanner scanner = new Scanner(file);
            while (true) {
                try {
                    commands.add(scanner.nextLine());
                } catch (NoSuchElementException e) {
                    break;
                }
            }
            for (int i = 0; i < commands.size(); i++) {
                String[] splittedCommand = ArrayUtils.addAll(commands.get(i).split(" ", 2), "");
                if (splittedCommand[0].equals("execute_script")) {
                    if (paths.add(splittedCommand[1])) {
                        try {
                            file = new File(splittedCommand[1]);
                            scanner = new Scanner(file);
                            int j = 1;
                            while (true) {
                                try {
                                    commands.add(i + j, scanner.nextLine());
                                    j++;
                                } catch (NoSuchElementException e) {
                                    break;
                                }
                            }
                            commands.add(i + j, "dev:EOF");
                        } catch (NullPointerException | FileNotFoundException e) {
                            InteractionWithClient.sendMessage(splittedCommand[1] + ": file with script not found, it can only be readable");
                        }
                    } else {
                        InteractionWithClient.sendMessage(commands.get(i) + ": command ignored because of recursion");
                    }
                } else if (splittedCommand[0].equals("insert")
                        | splittedCommand[0].equals("update")
                        | splittedCommand[0].equals("remove_lower")
                        | splittedCommand[0].equals("replace_if_lower")) {
                    ArrayList<String> toCSV = new ArrayList<>();
                    if (!splittedCommand[0].equals("remove_lower")) {
                        toCSV.add(splittedCommand[1]);
                    }
                    int j;
                    for (j = 1; j <= 9; j++) {
                        if (commands.get(i + j).equals("dev:EOF")) {
                            j += 2;
                            break;
                        } else if (commands.get(i + j).equals("") & j == 7) {
                            toCSV.add("");
                            toCSV.add("");
                            toCSV.add("");
                            j++;
                            break;
                        } else {
                            toCSV.add(commands.get(i + j));
                        }
                    }
                    i += j - 1;
                    RFC4180Parser rfc4180Parser = new RFC4180Parser();
                    String CSV = rfc4180Parser.parseToLine(toCSV.toArray(new String[0]), false);
                    try {
                        Application.requestMenu("dev:" + splittedCommand[0] + " " + CSV);
                    } catch (InvalidCommandException ignored) {
                        ignored.printStackTrace();
                    }
                } else if (splittedCommand[0].equals("dev:EOF")) {
                    //ignore
                } else {
                    try {
                        Application.requestMenu(commands.get(i));
                    } catch (InvalidCommandException e) {
                        InteractionWithClient.sendMessage(e.getMessage());
                    }
                }
            }
        } catch (NullPointerException | FileNotFoundException e) {
            InteractionWithClient.sendMessage(x + ": file with script not found, it can only be readable");
        }
    }
}
