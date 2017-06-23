package com.github.andreasbraun5.thesis.util;

import com.github.andreasbraun5.thesis.grammar.Terminal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AndreasBraun on 22.06.2017.
 */
public class Word {

    List<Terminal> terminals;


    public Word(String wordAsString) {
        List<Terminal> wordAsTerinalsList = new ArrayList<>();
        for (char c : wordAsString.toCharArray()) {
            wordAsTerinalsList.add(new Terminal(String.valueOf(c)));
        }
        terminals = wordAsTerinalsList;
    }

    public Word(List<Terminal> word) {
        terminals = word;
    }

    public List<Terminal> getTerminals() {
        return terminals;
    }

    public int getWordLength() {
        return terminals.size();
    }

    // TODO: Write Test
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Word word = (Word) o;

        return terminals.equals(word.terminals);
    }

    @Override
    public int hashCode() {
        return terminals.hashCode();
    }

    @Override
    public String toString() {
        return "Word{" +
                "terminals=" + terminals +
                '}';
    }
}
