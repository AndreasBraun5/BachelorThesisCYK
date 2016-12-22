package com.github.andreasbraun5.thesis.grammar;

import java.util.*;

/**
 * Created by Andreas Braun on 20.12.2016.
 */
public class GrammarProperties {

    public final List<Variable> variables = new ArrayList<>();
    public final List<Terminal> terminals = new ArrayList<>();

    public int sizeOfWord;
    public int numberOfVars;

    public void addVariables(Variable... vars) {
        Collections.addAll(this.variables, vars);
    }

    public void addTerminals(Terminal... terms) {
        Collections.addAll(this.terminals, terms);
    }

    public static GrammarProperties generateGrammarPropertiesFromWord(String word) {
        GrammarProperties grammarProperties = new GrammarProperties();
        Set<Character> terminals = new HashSet<>();
        for(int i=0;  i< word.length(); i++) {
            terminals.add(word.charAt(i));
        }
        for(Character t : terminals) {
            grammarProperties.addTerminals(new Terminal(terminals.toString()));
        }
        grammarProperties.numberOfVars = terminals.size();
        return grammarProperties;
    }

    @Override
    public String toString() {
        return "\nGrammarProperties:" +
                "\nsizeOfWord= " + sizeOfWord +
                "\nvariables= " + variables +
                "\nterminals= " + terminals;
    }
}
