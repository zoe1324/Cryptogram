package uk.co.team3.cryptogram;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LetterCryptogramTest {

    private LetterCryptogram crypt = new LetterCryptogram();

    @Test
    void encryptPhrase() {
        Map<Character, Character> cryptogramAlphabet = new HashMap<>();
        cryptogramAlphabet.put('t', 'a');
        cryptogramAlphabet.put('e', 'b');
        cryptogramAlphabet.put('s', 'c');
        crypt.setCryptogramAlphabet(cryptogramAlphabet);

        List<String> expected = new ArrayList<>();
        expected.add("a");
        expected.add("b");
        expected.add("c");
        expected.add("a");

        List<String> actual = crypt.encryptPhrase("test");
        assertEquals(expected, actual);
    }

    @Test
    void encryptPhraseWithSpaces() {
        Map<Character, Character> cryptogramAlphabet = new HashMap<>();
        cryptogramAlphabet.put('t', 'a');
        cryptogramAlphabet.put('e', 'b');
        cryptogramAlphabet.put('s', 'c');
        cryptogramAlphabet.put('p', 'd');
        cryptogramAlphabet.put('a', 'e');
        cryptogramAlphabet.put('c', 'f');
        crypt.setCryptogramAlphabet(cryptogramAlphabet);

        List<String> expected = new ArrayList<>();
        expected.add("a");
        expected.add("b");
        expected.add("c");
        expected.add("a");
        expected.add(" ");
        expected.add("c");
        expected.add("d");
        expected.add("e");
        expected.add("f");
        expected.add("b");
        expected.add("c");

        List<String> actual = crypt.encryptPhrase("test spaces");
        assertEquals(expected, actual);
    }

    @Test
    void encryptPhraseWithPunctuation() {
        Map<Character, Character> cryptogramAlphabet = new HashMap<>();
        cryptogramAlphabet.put('t', 'a');
        cryptogramAlphabet.put('e', 'b');
        cryptogramAlphabet.put('s', 'c');
        cryptogramAlphabet.put('p', 'd');
        cryptogramAlphabet.put('a', 'e');
        cryptogramAlphabet.put('c', 'f');
        crypt.setCryptogramAlphabet(cryptogramAlphabet);

        List<String> expected = new ArrayList<>();
        expected.add("a");
        expected.add("b");
        expected.add("c");
        expected.add("a");
        expected.add(",");
        expected.add("c");
        expected.add("d");
        expected.add("e");
        expected.add("f");
        expected.add("b");
        expected.add("c");

        List<String> actual = crypt.encryptPhrase("test,spaces");
        assertEquals(expected, actual);
    }

    @Test
    void encryptedPhraseWithPunctuationAndSpaces() {
        Map<Character, Character> cryptogramAlphabet = new HashMap<>();
        cryptogramAlphabet.put('t', 'a');
        cryptogramAlphabet.put('e', 'b');
        cryptogramAlphabet.put('s', 'c');
        cryptogramAlphabet.put('p', 'd');
        cryptogramAlphabet.put('a', 'e');
        cryptogramAlphabet.put('c', 'f');
        crypt.setCryptogramAlphabet(cryptogramAlphabet);

        List<String> expected = new ArrayList<>();
        expected.add("a");
        expected.add("b");
        expected.add("c");
        expected.add("a");
        expected.add(",");
        expected.add(" ");
        expected.add("c");
        expected.add("d");
        expected.add("e");
        expected.add("f");
        expected.add("b");
        expected.add("c");

        List<String> actual = crypt.encryptPhrase("test, spaces");
        assertEquals(expected, actual);
    }

    @Test
    void getPlainLetter() {
        Map<Character, Character> cryptogramAlphabet = (Map<Character, Character>) crypt.getCryptogramAlphabet();
        char encryptedLetter = cryptogramAlphabet.get('a');
        assertEquals('a', crypt.getPlainLetter(encryptedLetter));
    }
}