package abschlussaufgabe1;

import java.util.ArrayList;

/**
 * @author urlta
 * 
 *         The FormatClass is responsible for proper printing of values, and
 *         correct spacing. So that all numbers end on the same vertical line
 *
 */
public class FormatClass {

    /**
     * calculateLengthOfLongestWord calculates witch String connected to their
     * corresponding Numbers is and how long this connection is.
     * 
     * @param words  String List of all the names to be Printed (Vegetables, gold
     *               etc.)
     * @param values The values/value connected to these names (List must at least
     *               be as long as words)
     * @return length of the longest Combination of a Name with their associated
     *         value.
     */
    public static int calculatelengthOfLongestWordWithNumber(ArrayList<String> words, ArrayList<Integer> values) {
        int lengthOfLongestWord = 0;
        try {
            for (int i = 0; i < words.size(); i++) {
                if (lengthOfStringConnectedWithValue(words.get(i), values.get(i)) > lengthOfLongestWord) {
                    lengthOfLongestWord = lengthOfStringConnectedWithValue(words.get(i), values.get(i));
                }
            }
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            System.out.println("Error: the array for values has not enough entrys");
        }
        return lengthOfLongestWord;
    }

    /**
     * Adds the values to the String with enough spaces, that all numbers end on one
     * line(and adds a ": ") This results in the longest word being 2 longer, than
     * the longest Combination calculated before
     * 
     * @param words  Names to be Printed with Value
     * @param values Values which shall be Printed in one Line
     * @return String ArrayList, with appropriate Spacing for Printing
     */
    public static ArrayList<String> calculateWordsToPrint(ArrayList<String> words, ArrayList<Integer> values) {
        int lengthOfLongestWord = calculatelengthOfLongestWordWithNumber(words, values);
        ArrayList<String> wordsToPrint = new ArrayList<>();
        for (int i = 0; i < words.size(); i++) {
            wordsToPrint.add(words.get(i) + ": "
                    + wordWithXSpaces(
                            lengthOfLongestWord - lengthOfStringConnectedWithValue(words.get(i), values.get(i)))
                    + values.get(i));
        }
        return wordsToPrint;
    }

    private static int lengthOfNumber(int number) {
        int counter = 0;
        if (number == 0) { // could be removed, because nothing with value of 0
                           // will be printed in the current state of the Game
            return 1;
        }
        while (number != 0) {
            number = number / 10;
            counter++;
        }

        return counter;
    }

    private static int lengthOfStringConnectedWithValue(String word, int value) {
        return lengthOfNumber(value) + word.length();
    }

    private static String wordWithXSpaces(int x) {
        String wordOfSpaces = "";
        for (int i = 0; i < x; i++) {
            wordOfSpaces = wordOfSpaces + " ";
        }
        return wordOfSpaces;
    }

}
