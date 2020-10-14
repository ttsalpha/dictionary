package terminal;

import java.io.*;
import java.util.Scanner;


public class DictionaryManagement extends Dictionary {

    /**
     * export to file dictionaries.txt
     * word: target > pronunciation > explain
     */
    public static void dictionaryExportToFile() {
        final File fileName = new File("dictionaries.txt");
        try {
            FileWriter outputFile = new FileWriter(fileName);
            BufferedWriter fOut = new BufferedWriter(outputFile);
            for (Word w : dictionary) {
                fOut.write(w.getWord_target() + "\t" + w.getWord_explain() + "\n");
            }
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * import list word from file dictionaries.txt
     * add all to linked list dictionary
     */
    public static void insertFromFile() {
        final File fileName = new File("dictionaries.txt");
        try {
            try (FileReader inputFile = new FileReader(fileName)) {
                Scanner sc = new Scanner(inputFile);
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    Word word = new Word(line.split("\t", 2)[0], line.split("\t", 2)[1]);
                    dictionary.add(word);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
