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
    public VariableStart variableStart;

    public GrammarProperties(VariableStart varStart) {
        this.variableStart = varStart;
        this.variables.add(varStart);
    }

    public static GrammarProperties generatePartOfGrammarPropertiesFromWord(VariableStart startVariable, String word) {
        GrammarProperties grammarProperties = new GrammarProperties(startVariable);
        Set<Character> terminals = new HashSet<>();
        for(int i=0;  i< word.length(); i++) {
            terminals.add(word.charAt(i));
        }
        for(Character t : terminals) {
            grammarProperties.addTerminals(new Terminal(t.toString()));
        }
        grammarProperties.sizeOfWord = word.length();
        return grammarProperties;
    }

    /**
     *  not yet implemented yet
     */
    public static GrammarProperties generatePartOfGrammarPropertiesFromGrammar(Grammar grammar) {
        GrammarProperties grammarProperties = new GrammarProperties(grammar.getVariableStart());
        Map<Variable, List<Production>> productions = grammar.getProductionsMap();

        for(Map.Entry<Variable, List<Production>> entry : productions.entrySet()){
            // now we iterate over the map
            // TODO Later: Implement generatePartOfGrammarPropertiesFromGrammar.
        }
        return grammarProperties;
    }

    public GrammarProperties addVariables(Variable... vars) {
        Collections.addAll(this.variables, vars);
        return this;
    }

    public GrammarProperties addTerminals(Terminal... terms) {
        Collections.addAll(this.terminals, terms);
        return this;
    }

    public GrammarProperties addVariables(Set<Variable> variables){
        this.variables.addAll(variables);
        return this;
    }

    public GrammarProperties addTerminals(Set<Terminal> terminal){
        this.terminals.addAll(terminal);
        return this;
    }


    @Override
    public String toString() {
        return "GrammarProperties:" +
                "\nsizeOfWord= " + sizeOfWord +
                "\nvariables= " + variables +
                "\nterminals= " + terminals;
    }
}
