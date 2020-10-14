package terminal;

import java.util.LinkedList;

public class DictionaryCommandLine extends DictionaryManagement {

    /**
     * enters a string and returns a list for the list view
     *
     * @param text
     * @return linked list
     */
    public LinkedList<String> dictionaryLookup(String text) {
        LinkedList<String> list = new LinkedList<>();
        for (Word word : dictionary) {
            if (word.getWord_target().startsWith(text)) {
                list.add(word.getWord_target());
            }
        }
        return list;
    }

    /**
     * enters a string and returns meaning of word
     *
     * @param text
     * @return string
     */
    public String dictionarySearcher(String text) {
        for (Word word : dictionary) {
            if (word.getWord_target().equals(text)) {
                return word.getWord_explain();
            }
        }
        return "";
    }

    /**
     * enters a string and returns word of target (string)
     *
     * @param text
     * @return Word
     */
    public Word dictionaryFinder(String text) {
        for (Word word : dictionary) {
            if (word.getWord_target().equals(text)) {
                return word;
            }
        }
        return dictionary.get(0);
    }
}
