package com.github.andreasbraun5.thesis.util;

import com.github.andreasbraun5.thesis.grammar.Terminal;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AndreasBraun on 22.06.2017.
 */
@Getter
public class Word {

    List<Terminal> terminals;

    public static Word fromStringCharwise(String string) {
        List<Terminal> wordAsTerminalsList = new ArrayList<>();
        for (char c : string.toCharArray()) {
            wordAsTerminalsList.add(new Terminal(String.valueOf(c)));
        }
        return new Word(wordAsTerminalsList);
    }

    public Word(List<Terminal> word) {
        terminals = word;
    }

    public int getWordLength() {
        return terminals.size();
    }

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
        StringBuilder str = new StringBuilder();
        str.append("\nword:");
        terminals.forEach(terminal -> str.append(" ").append(terminal).append(""));
        str.deleteCharAt(str.length() - 1);
        str.deleteCharAt(str.length() - 1);
        return str.append(";").toString();
    }
}
