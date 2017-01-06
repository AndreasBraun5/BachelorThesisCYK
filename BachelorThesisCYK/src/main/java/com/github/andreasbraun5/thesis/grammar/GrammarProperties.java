package com.github.andreasbraun5.thesis.grammar;

import java.util.*;

/**
 * Created by Andreas Braun on 20.12.2016.
 */
public class GrammarProperties {

    public final Set<Variable> variables = new HashSet<>();
    public final Set<Terminal> terminals = new HashSet<>();

    public int sizeOfWord;
    public int maxNumberOfVarsPerCell;
    /**
     * If variableStart is not set, by default it will be "S".
     */
    public VariableStart variableStart =  new VariableStart("S");

    public GrammarProperties generatePartOfGrammarPropertiesFromWord(String word) {
        GrammarProperties grammarProperties = new GrammarProperties();
        Set<Character> terminals = new HashSet<>();
        for(int i=0;  i< word.length(); i++) {
            terminals.add(word.charAt(i));
        }
        for(Character t : terminals) {
            grammarProperties.addTerminals(new Terminal(terminals.toString()));
        }
        this.sizeOfWord = word.length();
        return grammarProperties;
    }

    /**
     *  not yet implemented yet
     */
    public GrammarProperties generatePartOfGrammarPropertiesFromGrammar(Grammar grammar) {
        GrammarProperties grammarProperties = new GrammarProperties();
        Map<Variable, List<Production>> productions = grammar.getProductionsMap();
        for(Map.Entry<Variable, List<Production>> entry : productions.entrySet()){
            // now we iterate over the map
            // TODO Later: Implement generatePartOfGrammarPropertiesFromGrammar.
        }


        return grammarProperties;
    }

    public void addVariables(Variable... vars) {
        Collections.addAll(this.variables, vars);
    }

    public void addTerminals(Terminal... terms) {
        Collections.addAll(this.terminals, terms);
    }

    public void addVariables(Set<Variable> variables){
        this.variables.addAll(variables);
    }

    public void addTerminals(Set<Terminal> terminal){
        this.terminals.addAll(terminal);
    }


    @Override
    public String toString() {
        return "GrammarProperties:" +
                "\nsizeOfWord= " + sizeOfWord +
                "\nvariables= " + variables +
                "\nterminals= " + terminals;
    }
}
