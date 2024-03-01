package application;

import support.Application;
import support.Document;

public class Main {
    public static void main(String[] args) {
        //all files in program root (collection: src/main/resources/csv)
        if (args.length != 0) {
            Document.setFileWithCollectionPath(args[0]);
            Application.fillMenu();
            Application.run();
        } else {
            System.out.println("Enter file with collection path in args[0]");
        }
    }
}