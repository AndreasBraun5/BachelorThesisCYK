package com.github.andreasbraun5.thesis.cyk;

import com.github.andreasbraun5.thesis.grammar.Terminal;
import com.github.andreasbraun5.thesis.grammar.Variable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Andreas Braun on 20.12.2016.
 */
public class GrammarProperties {

    public final List<Variable> variables = new ArrayList<>();
    public final List<Terminal> terminals = new ArrayList<>();

    public int sizeOfWord;

    public void addVariables(Variable... vars) {
        Collections.addAll(this.variables, vars);
    }

    public void addTerminals(Terminal... terms) {
        Collections.addAll(this.terminals, terms);
    }

}
