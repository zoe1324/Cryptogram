package uk.co.team3.cryptogram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract class Cryptogram {

    private Map<Character, ?> cryptogramAlphabet;
    private Map<Character, Integer> frequencies = new HashMap<>();
    private Map<Character, Integer> cryptoFrequencies = new HashMap<>();

    /**
     * Encrypts the given phrase using the implementations cryptogramAlphabet.
     * @param phrase - The phrase to be encrypted.
     * @return - List of Strings which is the encrypted phrase.
     */
    List<String> encryptPhrase(String phrase) {
        List<String> encryptedPhrase = new ArrayList<>();
        char f;
        for (char c : phrase.toLowerCase().toCharArray()) {
            if (cryptogramAlphabet.containsKey(c)) {
                encryptedPhrase.add(cryptogramAlphabet.get(c).toString());
            } else {
                encryptedPhrase.add(String.valueOf(c));
            }
            if(c == ' '| c == '?') {
                f = c;
            }
            else{
                f = cryptogramAlphabet.get(c).toString().charAt(0);
            }
            //Record letter frequencies
            if (cryptoFrequencies.containsKey(f)){
                int i = cryptoFrequencies.get(f) + 1;
                cryptoFrequencies.replace(f, i);
            } else {
                cryptoFrequencies.put(f, 1);
            }
            if (frequencies.containsKey(c)){
                int i = frequencies.get(c) + 1;
                frequencies.replace(c, i);
            } else {
                frequencies.put(c, 1);
            }
        }
        return encryptedPhrase;
    }

    /**
     * Gets the frequencies of the letters in the encrypted phrase.
     * @return - a map of the frequencies of each character.
     */
    Map<Character, Integer> getFrequencies() {
        return frequencies;
    }

    Map<Character, Integer> getCryptoFrequencies() {
        return cryptoFrequencies;
    }

    /**
     * Gets the cryptogram alphabet.
     * @return - A map of the cryptogram alphabet. The type of value of them map
     * is determined by the implementation.
     */
    Map<Character, ?> getCryptogramAlphabet() {
        return cryptogramAlphabet;
    }

    /**
     * Sets the cryptogram alphabet.
     * @param cryptogramAlphabet - The map which cryptogram alphabet should be set to.
     */
    void setCryptogramAlphabet(Map<Character, ?> cryptogramAlphabet) {
        this.cryptogramAlphabet = cryptogramAlphabet;
    }
}
