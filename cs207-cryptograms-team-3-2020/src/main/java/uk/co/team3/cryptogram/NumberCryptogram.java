package uk.co.team3.cryptogram;

import java.util.*;

class NumberCryptogram extends Cryptogram {

    private Map<Character, Integer> cryptogramAlphabet = new HashMap<>();

    /**
     * Constructs an instance of the LetterCryptogram.
     * Generates the alphabet, and generates a list which is the mapping of the natural numbers to
     * the alphabet and shuffles it.
     */
    NumberCryptogram() {
        List<Character> alphabet = new ArrayList<>();
        List<Integer> shuffledAlphabet = new ArrayList<>();

        for (char c = 'a'; c <= 'z'; c++) {
            alphabet.add(c);
        }

        for (int i = 0; i < 26; i++) {
            shuffledAlphabet.add(i);
        }

        Collections.shuffle(shuffledAlphabet);

        for (int i = 0; i < alphabet.size(); i++) {
            cryptogramAlphabet.put(alphabet.get(i), shuffledAlphabet.get(i));
        }

        setCryptogramAlphabet(cryptogramAlphabet);
    }

    /**
     * Gets the plain letter from the encrypted one.
     * @param i - Encrypted number to be decrypted.
     * @return - The plain, decrypted letter.
     */
    char getPlainLetter(int i) {
        for (Map.Entry<Character, Integer> entry : cryptogramAlphabet.entrySet()) {
            if (entry.getValue().equals(i)) {
                return entry.getKey();
            }
        }
        return '\0';
    }
}
