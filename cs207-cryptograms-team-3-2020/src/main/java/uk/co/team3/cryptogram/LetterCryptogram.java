package uk.co.team3.cryptogram;

import java.util.*;

class LetterCryptogram extends Cryptogram {

    private Map<Character, Character> cryptogramAlphabet = new HashMap<>();

    /**
     * Constructs an instance of the LetterCryptogram.
     * Generates the alphabet and then shuffles it to create the cryptogramAlphabet.
     */
    LetterCryptogram() {
        List<Character> alphabet = new ArrayList<>();
        List<Character> shuffledAlphabet = new ArrayList<>();

        for (char c = 'a'; c <= 'z'; c++) {
            alphabet.add(c);
            shuffledAlphabet.add(c);
        }

        Collections.shuffle(shuffledAlphabet);

        for (int i = 0; i < alphabet.size(); i++) {
            cryptogramAlphabet.put(alphabet.get(i), shuffledAlphabet.get(i));
        }

        setCryptogramAlphabet(cryptogramAlphabet);
    }

    /**
     * Gets the plain letter from the encrypted one.
     * @param c - Encrypted letter to be decrypted.
     * @return - The plain, decrypted letter.
     */
    public char getPlainLetter(char c) {
        for (Map.Entry<Character, Character> entry : cryptogramAlphabet.entrySet()) {
            if (entry.getValue().equals(c)) {
                return entry.getKey();
            }
        }
        return '\0';
    }
}

