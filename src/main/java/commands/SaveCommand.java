package commands;

import data.StudyGroup;
import support.parser.Parser;
import support.Document;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class SaveCommand extends Command {
    @Override
    public void execute(String x) {
        File file = new File(Document.getFileWithCollectionPath());
        try {
            FileWriter fileWriter = new FileWriter(file, false);
            Document.getCustomHashMap().entrySet().forEach(el -> {
                try {
                    fileWriter.write(Parser.parseEntryToCSV(el) + "\n");
                } catch (IOException e) {
                    System.out.println("No write access");
                }
            });
            fileWriter.flush();
        } catch (IOException e) {
            System.out.println("No write access");
        }
    }
}
