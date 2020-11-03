package uk.co.team3.cryptogram;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NumberCryptogramTest {

    private NumberCryptogram crypt = new NumberCryptogram();

    @Test
    void encryptPhrase() {
        Map<Character, Character> cryptogramAlphabet = new HashMap<>();
        cryptogramAlphabet.put('t', '1');
        cryptogramAlphabet.put('e', '2');
        cryptogramAlphabet.put('s', '3');
        crypt.setCryptogramAlphabet(cryptogramAlphabet);

        List<String> expected = new ArrayList<>();
        expected.add("1");
        expected.add("2");
        expected.add("3");
        expected.add("1");

        List<String> actual = crypt.encryptPhrase("test");
        assertEquals(expected, actual);
    }

    @Test
    void encryptPhraseWithSpaces() {
        Map<Character, Character> cryptogramAlphabet = new HashMap<>();
        cryptogramAlphabet.put('t', '1');
        cryptogramAlphabet.put('e', '2');
        cryptogramAlphabet.put('s', '3');
        cryptogramAlphabet.put('p', '4');
        cryptogramAlphabet.put('a', '5');
        cryptogramAlphabet.put('c', '6');
        crypt.setCryptogramAlphabet(cryptogramAlphabet);

        List<String> expected = new ArrayList<>();
        expected.add("1");
        expected.add("2");
        expected.add("3");
        expected.add("1");
        expected.add(" ");
        expected.add("3");
        expected.add("4");
        expected.add("5");
        expected.add("6");
        expected.add("2");
        expected.add("3");

        List<String> actual = crypt.encryptPhrase("test spaces");
        assertEquals(expected, actual);
    }

    @Test
    void encryptPhraseWithPunctuation() {
        Map<Character, Character> cryptogramAlphabet = new HashMap<>();
        cryptogramAlphabet.put('t', '1');
        cryptogramAlphabet.put('e', '2');
        cryptogramAlphabet.put('s', '3');
        cryptogramAlphabet.put('p', '4');
        cryptogramAlphabet.put('a', '5');
        cryptogramAlphabet.put('c', '6');
        crypt.setCryptogramAlphabet(cryptogramAlphabet);

        List<String> expected = new ArrayList<>();
        expected.add("1");
        expected.add("2");
        expected.add("3");
        expected.add("1");
        expected.add(",");
        expected.add("3");
        expected.add("4");
        expected.add("5");
        expected.add("6");
        expected.add("2");
        expected.add("3");

        List<String> actual = crypt.encryptPhrase("test,spaces");
        assertEquals(expected, actual);
    }

    @Test
    void encryptedPhraseWithPunctuationAndSpaces() {
        Map<Character, Character> cryptogramAlphabet = new HashMap<>();
        cryptogramAlphabet.put('t', '1');
        cryptogramAlphabet.put('e', '2');
        cryptogramAlphabet.put('s', '3');
        cryptogramAlphabet.put('p', '4');
        cryptogramAlphabet.put('a', '5');
        cryptogramAlphabet.put('c', '6');
        crypt.setCryptogramAlphabet(cryptogramAlphabet);

        List<String> expected = new ArrayList<>();
        expected.add("1");
        expected.add("2");
        expected.add("3");
        expected.add("1");
        expected.add(",");
        expected.add(" ");
        expected.add("3");
        expected.add("4");
        expected.add("5");
        expected.add("6");
        expected.add("2");
        expected.add("3");

        List<String> actual = crypt.encryptPhrase("test, spaces");
        assertEquals(expected, actual);
    }

    @Test
    void getPlainLetter() {
        Map<Character, Integer> cryptogramAlphabet = (Map<Character, Integer>) crypt.getCryptogramAlphabet();
        int encryptedLetter = cryptogramAlphabet.get('a');
        assertEquals('a', crypt.getPlainLetter(encryptedLetter));
    }
}