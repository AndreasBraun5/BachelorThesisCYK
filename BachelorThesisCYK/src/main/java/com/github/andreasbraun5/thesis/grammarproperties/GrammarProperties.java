package com.github.andreasbraun5.thesis.grammarproperties;

import com.github.andreasbraun5.thesis.grammar.*;

import java.time.temporal.ValueRange;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Andreas Braun on 20.12.2016.
 * https://github.com/AndreasBraun5/
 * GrammarProperties only contains the absolute minimum information about the grammar.
 */
public class GrammarProperties {

    public final Set<Variable> variables = new HashSet<>(); // obligatory attribute
    public final Set<Terminal> terminals = new HashSet<>(); // obligatory attribute
    public VariableStart variableStart; // obligatory attribute

    public GrammarConstraints grammarConstraints =
            new GrammarConstraints();  // optional, uses default values.
    public ExamConstraints examConstraints
            = new ExamConstraints(); // optional, uses default values.

    public GrammarProperties(VariableStart varStart) {
        this.variableStart = varStart;
        this.variables.add(varStart);
    }

    public Set<Variable> getVariables() {
        return Collections.unmodifiableSet(variables);
    }

    public GrammarProperties(VariableStart varStart, Set<Variable> variables, Set<Terminal> terminals) {
        this(varStart);
        this.variables.addAll(variables);
        this.terminals.addAll(terminals);
    }

    /**
     * Generates not all obligatory attributes of the GrammarProperties. E.g. terminal and VariableStart, but
     * variables is missing. Also sets the sizeOf the Word variable.
     */
    public static GrammarProperties generatePartOfGrammarPropertiesFromWord(VariableStart startVariable, String word) {
        GrammarProperties grammarProperties = new GrammarProperties(startVariable);
        Set<Character> terminals = new HashSet<>();
        for (int i = 0; i < word.length(); i++) {
            terminals.add(word.charAt(i));
        }
        for (Character t : terminals) {
            grammarProperties.addTerminals(new Terminal(t.toString()));
        }
        grammarProperties.grammarConstraints.sizeOfWord = word.length();
        return grammarProperties;
    }

    /**
     * Generates all obligatory attributes of GrammarProperties. E.g. variables, terminals, variableStart
     */
    public static GrammarProperties generatePartOfGrammarPropertiesFromGrammar(Grammar grammar) {
        GrammarProperties grammarProperties = new GrammarProperties(grammar.getVariableStart());
        List<Production> productions = grammar.getProductionsAsList();
        for (Production tempProd : productions) {
            grammarProperties.addVariables(tempProd.getLeftHandSideElement());
            if (tempProd.getRightHandSideElement() instanceof Terminal) {
                grammarProperties.addTerminals((Terminal) tempProd.getRightHandSideElement());
            }
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

    public GrammarProperties addVariables(Set<Variable> variables) {
        this.variables.addAll(variables);
        return this;
    }

    public GrammarProperties addTerminals(Set<Terminal> terminal) {
        this.terminals.addAll(terminal);
        return this;
    }

    @Override
    public String toString() {
        return "GrammarProperties: {" +
                "\n		variables= " + variables +
                "\n		variableStart= " + variableStart +
                "\n		terminals= " + terminals +
                grammarConstraints.toString() +
                examConstraints.toString() +
                "\n}";
    }
}
